package com.kuronami.questbroadcast;

import com.kuronami.questbroadcast.broadcast.BroadcastFormatter;
import com.kuronami.questbroadcast.broadcast.RecentCompletions;
import dev.architectury.event.EventResult;
import dev.ftb.mods.ftbquests.events.ObjectCompletedEvent;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

/**
 * FTB Quests のクエスト完了をサーバー上の全プレイヤーのチャットへ告知する。
 *
 * <p>リスナーは Architectury の静的イベント ({@code ObjectCompletedEvent.QUEST}) に登録する。
 * このイベントはサーバー側だけでなくクライアント側でも同期目的で発火されるため
 * （FTB Quests {@code client/FTBQuestsNetClient#fireCompletedEvent}、同 {@code QuestProgressEventData#forClient}
 * は {@code onlineMembers = List.of()} を渡す）、発報は必ずサーバー側 1 回だけに絞る。</p>
 *
 * <p>設定ファイルは持たない（入れただけで効く・既定が製品）。挙動を変えたい場合は本体側の
 * toast 無効化等と組み合わせること。</p>
 */
public final class CompletionBroadcast {

    /** 完了告知の翻訳キー。引数は順に プレイヤー名(代表) / クエストタイトル。 */
    public static final String KEY_QUEST_COMPLETED = "message." + Constants.MOD_ID + ".quest_completed";

    private static volatile boolean listenerRegistered = false;

    private CompletionBroadcast() {
    }

    /**
     * リスナーを登録する。両 loader のエントリポイントから 1 回だけ呼ばれること。
     */
    public static void init() {
        ObjectCompletedEvent.QUEST.register(CompletionBroadcast::onQuestCompleted);
        listenerRegistered = true;
        Constants.LOG.info("Quest completion broadcast enabled");
    }

    /** GameTest / 診断用: リスナーの登録が済んでいるか。
     *
     * @return {@link #init()} 呼び出し済みであれば true
     */
    public static boolean isListenerRegistered() {
        return listenerRegistered;
    }

    /**
     * クエスト完了イベントの処理。常に {@link EventResult#pass()} を返す
     * （この MOD は完了処理に介入しない・告知だけする）。
     *
     * <ul>
     *   <li>ガード1（一次）: クライアント側発火は無視する。クライアントでは
     *       {@code ClientQuestFile#getSide() == Env.CLIENT} のため {@code isServerSide()} が false を返す。</li>
     *   <li>ガード2: チームのオンラインメンバーが空なら送れないので何もしない。</li>
     *   <li>保険: 同一チーム×同一クエストの多段発火（issue #1269 系）を有界 LRU で握りつぶす。</li>
     * </ul>
     */
    private static EventResult onQuestCompleted(ObjectCompletedEvent.QuestEvent event) {
        if (!event.getQuest().getQuestFile().isServerSide()) {
            return EventResult.pass();
        }

        List<ServerPlayer> onlineMembers = event.getOnlineMembers();
        if (onlineMembers.isEmpty()) {
            return EventResult.pass();
        }

        String key = BroadcastFormatter.completionKey(event.getData().getTeamId(), event.getQuest().id);
        if (!RecentCompletions.tryMark(key)) {
            return EventResult.pass();
        }

        List<String> names = onlineMembers.stream()
                .map(member -> member.getGameProfile().getName())
                .toList();
        String questTitle = event.getQuest().getTitle().getString();
        Component message = Component.translatable(KEY_QUEST_COMPLETED,
                BroadcastFormatter.joinPlayerNames(names), questTitle);

        // ガード1 を通過済みなので ServerQuestFile へのキャストは安全。
        // チームメンバーだけでなくサーバーに居る全プレイヤーへ送るのがこの MOD の目的。
        MinecraftServer server = ((ServerQuestFile) event.getQuest().getQuestFile()).server;
        for (ServerPlayer recipient : server.getPlayerList().getPlayers()) {
            recipient.sendSystemMessage(message);
        }
        return EventResult.pass();
    }
}
