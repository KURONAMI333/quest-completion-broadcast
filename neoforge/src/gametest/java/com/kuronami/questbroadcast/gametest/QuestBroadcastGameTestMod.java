package com.kuronami.questbroadcast.gametest;

import net.neoforged.fml.common.Mod;

/**
 * GameTest を持つためだけの開発専用 mod。出荷 jar には入らない
 * （{@code neoforge/src/gametest} は jar タスクの入力ではなく、
 * {@code ./gradlew :neoforge:runGameTestServer} の実行時クラスパスにだけ載る）。
 *
 * <p>テストの登録は NeoForge の {@code GameTestHooks.registerGametests()} が
 * {@code ModFileScanData} から {@code @GameTestHolder} を拾って行うので、ここに登録の配線は無い。
 * {@code javafml} は modid に対応する {@code @Mod} クラスを 1 つ要求する。</p>
 *
 * <p>テスト ID の名前空間は {@code @GameTestHolder(Constants.MOD_ID)} のまま（{@code questbroadcast}）。
 * {@code neoforge.enabledGameTestNamespaces} はその名前空間で絞り込む
 * （この mod の id ではない）。</p>
 */
@Mod(QuestBroadcastGameTestMod.MOD_ID)
public class QuestBroadcastGameTestMod {

    public static final String MOD_ID = "questbroadcast_gametest";

    public QuestBroadcastGameTestMod() {
    }
}
