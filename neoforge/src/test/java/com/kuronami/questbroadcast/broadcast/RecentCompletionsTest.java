package com.kuronami.questbroadcast.broadcast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecentCompletionsTest {

    @BeforeEach
    void reset() {
        RecentCompletions.resetForTest();
    }

    @Test
    void firstMarkIsAccepted() {
        assertTrue(RecentCompletions.tryMark("team-a:100"));
    }

    @Test
    void duplicateMarkIsRefused() {
        assertTrue(RecentCompletions.tryMark("team-a:100"));
        assertFalse(RecentCompletions.tryMark("team-a:100"));
    }

    @Test
    void differentQuestsAreIndependent() {
        assertTrue(RecentCompletions.tryMark("team-a:100"));
        assertTrue(RecentCompletions.tryMark("team-a:101"));
        assertFalse(RecentCompletions.tryMark("team-a:101"));
    }

    /**
     * 注意: accessOrder=true のため、満タン後に既存キーへ触れると access 順が更新され
     * 「どのキーが追い出されるか」が変わる。検証は「触らず追加」→「追い出し確認」の順で行う。
     */
    @Test
    void oldestEntryIsEvictedBeyondCapacity() {
        for (int i = 0; i < RecentCompletions.CAPACITY; i++) {
            assertTrue(RecentCompletions.tryMark("t:" + i), "entry " + i + " should be fresh");
        }
        // 容量ちょうどでは何も追い出されない（既存キーへは触れない）。
        assertTrue(RecentCompletions.tryMark("t:" + RecentCompletions.CAPACITY));
        // 1 件追加したので最古の "t:0" が捨てられ、再受理される。
        assertTrue(RecentCompletions.tryMark("t:0"), "evicted key must be accepted again");
        // 直近に追加したキーはまだ覚えている。
        assertFalse(RecentCompletions.tryMark("t:" + RecentCompletions.CAPACITY));
        // "t:0" の再受理で次の最古 "t:1" が追い出されるが、"t:2" 以降は残る。
        assertFalse(RecentCompletions.tryMark("t:2"));
    }

    @Test
    void resetClearsEverything() {
        assertTrue(RecentCompletions.tryMark("t:1"));
        RecentCompletions.resetForTest();
        assertTrue(RecentCompletions.tryMark("t:1"));
    }
}
