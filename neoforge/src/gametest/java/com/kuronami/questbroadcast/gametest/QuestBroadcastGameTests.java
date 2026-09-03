package com.kuronami.questbroadcast.gametest;

import com.kuronami.questbroadcast.CompletionBroadcast;
import com.kuronami.questbroadcast.Constants;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/**
 * 初期化確認の GameTest。この MOD の挙動本体（チャット告知）はプレイヤーの join を要するため
 * headless では assert できない＝ここで見るのは「mod と FTB スタックごとの起動・リスナー登録」まで。
 *
 * <p>実挙動の検証は dev サーバーでの目視（RELEASE_HANDOFF.md の検証ログ参照）。</p>
 */
@GameTestHolder(Constants.MOD_ID)
public class QuestBroadcastGameTests {

    @PrefixGameTestTemplate(false)
    @GameTest(template = "empty3x3x3")
    public static void completionListenerIsRegistered(GameTestHelper helper) {
        if (!CompletionBroadcast.isListenerRegistered()) {
            helper.fail("entry-point init did not run; ObjectCompletedEvent.QUEST listener is missing");
            return;
        }
        helper.succeed();
    }
}
