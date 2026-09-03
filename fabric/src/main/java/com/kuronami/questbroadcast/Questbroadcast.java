package com.kuronami.questbroadcast;

import net.fabricmc.api.ModInitializer;

/**
 * Fabric 側エントリポイント。共通初期化に委譲するだけ。
 */
public class Questbroadcast implements ModInitializer {

    /** Fabric ローダーから呼ばれる既定コンストラクタ。 */
    public Questbroadcast() {
    }

    @Override
    public void onInitialize() {
        CommonClass.init();
    }
}
