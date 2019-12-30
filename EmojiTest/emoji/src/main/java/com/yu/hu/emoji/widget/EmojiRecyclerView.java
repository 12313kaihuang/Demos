package com.yu.hu.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.yu.hu.emoji.entity.Emoji;
import com.yu.hu.emoji.util.TransformUtils;

import java.util.List;

/**
 * Created by Hy on 2019/12/30 17:42
 * 用于显示表情列表
 *
 * @see #setEmojis(List)
 **/
public class EmojiRecyclerView extends RecyclerView {

    public EmojiRecyclerView(@NonNull Context context) {
        super(context);
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmojis(List<Emoji> emojiList) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        setLayoutManager(layoutManager);
        setNestedScrollingEnabled(false); //禁用滑动
        EmojiAdapter emojiAdapter = new EmojiAdapter();
        setAdapter(emojiAdapter);
        emojiAdapter.submitList(emojiList);
    }

    private static class EmojiAdapter extends ListAdapter<Emoji, ViewHolder> {

        EmojiAdapter() {
            super(new DiffUtil.ItemCallback<Emoji>() {
                @Override
                public boolean areItemsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
                    return oldItem.emojiRes == newItem.emojiRes;
                }
            });
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            EmojiView imageView = new EmojiView(parent.getContext());
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = TransformUtils.dip2px(parent.getContext(), 8);
            imageView.setLayoutParams(layoutParams);
            return new ViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Emoji emoji = getItem(position);
            ((EmojiView) holder.itemView).setImageResource(emoji.emojiRes);
        }

    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
