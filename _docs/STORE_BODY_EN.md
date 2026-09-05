When a team completes a quest, everyone on the server sees it in chat. FTB Quests tells the team that finished; it does not tell anyone else.

```
Steve completed the quest "Getting Started"
```

On a server where several teams are working through the same quest book, this is the difference between everyone racing in silence and everyone seeing where the others are. Nothing else changes — the quest book, the rewards and the progression are untouched.

**No configuration.** There is no config file and no commands. Install it and it works.

The same completion is never announced twice. FTB Quests replays completion events on the client for GUI sync, and fires them again when a team's progress is recalculated; both are filtered out.

Messages are localized, so each player reads them in their own language. 9 bundled: German, English, Spanish, French, Japanese, Korean, Portuguese (Brazil), Russian, Simplified Chinese.

**Install**

1. Install FTB Quests (and its dependencies: FTB Library, FTB Teams, Architectury).
2. Drop this in your `mods` folder. Server side is what matters; it is safe on the client too.

**Dependencies**

- [FTB Quests](https://www.curseforge.com/minecraft/mc-mods/ftb-quests) 2101.1.34+ — **required**

FTB Quests is published on CurseForge only, so a Modrinth-only setup cannot resolve it.

**Scope and limitations**

- Announcements are chat messages. The mod does not add a toast, an overlay or a sound.
- It announces quest completions. Chapter completions, quest starts and reward claims are not announced.
- Team-based: the message names the player who finished it for the team.

Bugs and questions: comment on the CurseForge page, or DM @kuronami333 on X.

All Rights Reserved. Modpack inclusion is allowed without permission or credit. Source: https://github.com/KURONAMI333/quest-completion-broadcast
