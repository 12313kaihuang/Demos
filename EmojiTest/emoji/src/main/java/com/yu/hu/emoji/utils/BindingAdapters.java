package com.yu.hu.emoji.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.yu.hu.emoji.entity.Emoji;
import com.yu.hu.emoji.widget.EmojiRecyclerView;
import com.yu.hu.emoji.widget.IEmojiable;

import java.util.List;

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
        } else {
            throw new RuntimeException(view.getClass().getName() + " has not implements IEmojiable");
        }
    }

    //used in layout_emoji_list.xml
    @BindingAdapter("emojis")
    public static void setEmojis(EmojiRecyclerView recyclerView, List<Emoji> emojiList) {
        recyclerView.setEmojis(emojiList);
    }
}
