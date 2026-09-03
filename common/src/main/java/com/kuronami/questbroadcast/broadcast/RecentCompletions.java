package com.kuronami.questbroadcast.broadcast;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 直近に発報済みの完了キーを覚えておく有界 LRU。
 *
 * <p>FTB Quests issue #1269 系の「同一クエスト完了で複数回イベントが飛ぶ」ケースへの保険。
 * キーの総数はクエスト数 × チーム数程度で飽和するため、256 件の LRU で十分に重複を拾える。
 * MC の型に依存しない純粋な実装なので単体テスト可能。</p>
 */
public final class RecentCompletions {

    /** 保持する最大エントリ数。 */
    public static final int CAPACITY = 256;

    private static final Set<String> RECENT = Collections.newSetFromMap(
            Collections.synchronizedMap(new LinkedHashMap<>(CAPACITY * 4 / 3 + 1, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
                    return size() > CAPACITY;
                }
            }));

    private RecentCompletions() {
    }

    /**
     * キーを「発報済み」として記録する。
     *
     * @param key 発報単位の一意キー（例: {@code "teamId:questId"}）
     * @return 初めてのキーなら true（発報してよい）。既に記録済みなら false（発報済み）。
     *         {@code add} は内部 map の 1 回の {@code put} として動くため、呼び出しは原子操作。
     */
    public static boolean tryMark(String key) {
        return RECENT.add(key);
    }

    /**
     * 状態を空にする。単体テスト専用。
     */
    static void resetForTest() {
        synchronized (RECENT) {
            RECENT.clear();
        }
    }
}
