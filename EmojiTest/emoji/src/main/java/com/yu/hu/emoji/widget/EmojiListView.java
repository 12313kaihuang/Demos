package com.yu.hu.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.yu.hu.emoji.EmojiManager;
import com.yu.hu.emoji.R;
import com.yu.hu.emoji.adapter.EmojiAdapter;
import com.yu.hu.emoji.databinding.LayoutEmojiListBinding;
import com.yu.hu.emoji.entity.Emoji;

import java.util.List;

/**
 * Created by Hy on 2020/01/06 18:30
 **/
public class EmojiListView extends LinearLayout implements EmojiAdapter.OnItemClickListener {

    private EmojiAdapter.OnItemClickListener mItemClickListner;

    public EmojiListView(Context context) {
        this(context, null);
    }

    public EmojiListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutEmojiListBinding dataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_emoji_list, this, true);

        EmojiData emojiData = new EmojiData();
        emojiData.recentEmojis = EmojiManager.getRecentEmoji();
        emojiData.qqEmojis = EmojiManager.getAllQQEmoji();
        emojiData.defaultEmojis = EmojiManager.getAllEmoji();

        dataBinding.recentEmojiList.setOnItemClickListner(this);
        dataBinding.qqEmojiList.setOnItemClickListner(this);
        dataBinding.defaultEmojiList.setOnItemClickListner(this);

        dataBinding.setEmojiData(emojiData);
    }

    /**
     * 添加item点击事件
     */
    public void setOnItemClickListner(EmojiAdapter.OnItemClickListener itemClickListner) {
        this.mItemClickListner = itemClickListner;
    }

    @Override
    public void onItemClick(Emoji emoji) {
        if (mItemClickListner != null) {
            mItemClickListner.onItemClick(emoji);
        }
    }

    public static class EmojiData {
        public List<Emoji> recentEmojis;
        public List<Emoji> qqEmojis;
        public List<Emoji> defaultEmojis;
    }
}
