package com.kuronami.questbroadcast;

/**
 * 共有ロジックの起動点。両 loader のエントリポイントから呼ばれる。
 * loader 固有の差分は無いので、ここで common の初期化を 1 回行うだけ。
 */
public class CommonClass {

    public static void init() {
        CompletionBroadcast.init();
    }
}
