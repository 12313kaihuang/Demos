package com.yu.hu.emoji.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.yu.hu.emoji.widget.IEmojiable;

/**
 * Created by Hy on 2019/12/31 11:50
 **/
@SuppressWarnings("unused")
public class BindingAdapters {

    //used in activity_emoji_test.xml
    @BindingAdapter("emojiText")
    public static void setEmojiText(View view, String text) {
        if (view instanceof IEmojiable) {
            ((IEmojiable) view).setEmojiText(text);
        }
    }

}
