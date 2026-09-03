package com.kuronami.questbroadcast.broadcast;

import java.util.List;
import java.util.UUID;

/**
 * 告知文面の組み立てに使う純粋関数群。Minecraft の型に依存しないので単体テスト可能。
 */
public final class BroadcastFormatter {

    private BroadcastFormatter() {
    }

    /**
     * プレイヤー名のリストを「A, B, C」形式へ整える。
     *
     * @param names プレイヤー名のリスト（null 要素と空文字は無視される）
     * @return カンマ＋スペース結合の名前列。全要素が無効なら空文字
     *         （翻訳文の %s が空になるだけで壊れない）
     */
    public static String joinPlayerNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (String name : names) {
            if (name == null || name.isBlank()) {
                continue;
            }
            if (out.length() > 0) {
                out.append(", ");
            }
            out.append(name.trim());
        }
        return out.toString();
    }

    /**
     * LRU 用の安定したキー「teamId:questId」。
     *
     * @param teamId  FTB Quests チームの UUID ({@code TeamData#getTeamId()})
     * @param questId クエスト ID ({@code QuestObject#id})
     */
    public static String completionKey(UUID teamId, long questId) {
        return teamId + ":" + questId;
    }
}
