package com.yu.hu.emojitest;

import android.app.Application;

import com.yu.hu.emoji.EmojiManager;

/**
 * Created by Hy on 2019/12/30 12:48
 **/
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EmojiManager.init();
    }
}
