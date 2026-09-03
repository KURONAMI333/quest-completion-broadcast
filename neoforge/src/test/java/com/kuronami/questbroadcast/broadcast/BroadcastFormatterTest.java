package com.kuronami.questbroadcast.broadcast;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BroadcastFormatterTest {

    @Test
    void singleNameHasNoSeparator() {
        assertEquals("Steve", BroadcastFormatter.joinPlayerNames(List.of("Steve")));
    }

    @Test
    void multipleNamesAreCommaJoined() {
        assertEquals("Steve, Alex, Notch",
                BroadcastFormatter.joinPlayerNames(Arrays.asList("Steve", "Alex", "Notch")));
    }

    @Test
    void blankAndNullNamesAreSkipped() {
        assertEquals("Steve, Alex",
                BroadcastFormatter.joinPlayerNames(Arrays.asList(null, "", "  ", "Steve", "Alex")));
    }

    @Test
    void emptyListYieldsEmptyString() {
        assertEquals("", BroadcastFormatter.joinPlayerNames(List.of()));
        assertEquals("", BroadcastFormatter.joinPlayerNames(null));
    }

    @Test
    void allInvalidYieldsEmptyString() {
        assertEquals("", BroadcastFormatter.joinPlayerNames(Arrays.asList("", "   ", null)));
    }

    @Test
    void completionKeyIsStableTeamColonQuest() {
        UUID team = UUID.fromString("00000000-0000-0000-0000-000000000001");
        assertEquals(team + ":12345", BroadcastFormatter.completionKey(team, 12345L));
        assertEquals(team + ":0", BroadcastFormatter.completionKey(team, 0L),
                "FTB Quests のルートオブジェクトは id=0 を持つことがあるが、それも衝突なく扱える");
    }

    @Test
    void sameTeamDifferentQuestCollideNot() {
        UUID team = UUID.randomUUID();
        String k1 = BroadcastFormatter.completionKey(team, 1L);
        String k2 = BroadcastFormatter.completionKey(team, 2L);
        assertEquals(false, k1.equals(k2));
    }
}
