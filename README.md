# Quest Completion Broadcast

Announces FTB Quests completions to all players in chat. Drop it in — it just works (no config, by design).

- Host mod: [FTB Quests](https://www.curseforge.com/minecraft/mc-mods/ftb-quests) 2101.1.34+ (required)
- Minecraft 1.21.1 / NeoForge 21.1.227+ / Fabric Loader 0.16.9+ (+ Fabric API)

When a team completes a quest, every player on the server sees a message like
`Steve completed the quest "Getting Started"` (translated into each client's language;
9 languages bundled: de/en/es/fr/ja/ko/pt_br/ru/zh_cn). Duplicate broadcasts for the same
team and quest are suppressed, including the client-side event replays FTB Quests fires
for GUI sync.

License: All Rights Reserved.
