package com.yu.hu.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yu.hu.emoji.adapter.EmojiAdapter;
import com.yu.hu.emoji.entity.Emoji;
import com.yu.hu.emoji.utils.EmojiDecoration;

import java.util.List;

/**
 * Created by Hy on 2019/12/30 17:42
 * 用于显示表情列表
 *
 * @see #setEmojis(List)
 * @see #setEmojis(List, int)
 * @see #setEmojis(List, int, int)
 **/
public class EmojiRecyclerView extends RecyclerView {

    private static final int DEFAULT_SPAN_COUNT = 7;  //默认一行显示7个表情
    private static final int DEFAULT_BOTTOM_OFFSET = 8; //默认底部间隔8dp

    private EmojiAdapter mEmojiAdapter;

    public EmojiRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @SuppressWarnings("unused")
    private void init(Context context) {
        mEmojiAdapter = new EmojiAdapter();
    }

    /**
     * 设置表情
     *
     * @param emojiList emojiList
     */
    public void setEmojis(List<Emoji> emojiList) {
        setEmojis(emojiList, DEFAULT_SPAN_COUNT);
    }

    /**
     * 设置表情
     *
     * @param emojiList emojiList
     * @param spanCount 每行表情数
     */
    public void setEmojis(List<Emoji> emojiList, int spanCount) {
        setEmojis(emojiList, spanCount, DEFAULT_BOTTOM_OFFSET);
    }

    /**
     * 设置表情
     *
     * @param emojiList    emojiList
     * @param spanCount    每行表情数
     * @param bottomOffect 每行表情底部间隔
     */
    public void setEmojis(List<Emoji> emojiList, int spanCount, int bottomOffect) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        setLayoutManager(layoutManager);
        setNestedScrollingEnabled(false); //禁用滑动
        setAdapter(mEmojiAdapter);
        addItemDecoration(new EmojiDecoration(bottomOffect));
        mEmojiAdapter.submitList(emojiList);
    }

    /**
     * 添加item点击事件
     */
    public void setOnItemClickListner(EmojiAdapter.OnItemClickListener itemClickListner) {
        mEmojiAdapter.setOnItemClickListner(itemClickListner);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
