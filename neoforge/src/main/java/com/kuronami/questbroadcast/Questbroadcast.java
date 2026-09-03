package com.kuronami.questbroadcast;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge 側エントリポイント。共通初期化に委譲するだけ。
 */
@Mod(Constants.MOD_ID)
public class Questbroadcast {

    public Questbroadcast(IEventBus eventBus) {
        CommonClass.init();
    }
}
