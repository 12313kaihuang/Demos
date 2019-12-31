package com.yu.hu.emoji.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.yu.hu.emoji.entity.Emoji;
import com.yu.hu.emoji.widget.EmojiRecyclerView;
import com.yu.hu.emoji.widget.EmojiView;

/**
 * Created by Hy on 2019/12/31 11:24
 *
 * @see #setOnItemClickListner(OnItemClickListener)  item点击事件
 **/
public class EmojiAdapter extends ListAdapter<Emoji, EmojiRecyclerView.ViewHolder> {

    private OnItemClickListener itemClickListner;

    public EmojiAdapter() {
        super(new DiffUtil.ItemCallback<Emoji>() {
            @Override
            public boolean areItemsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Emoji oldItem, @NonNull Emoji newItem) {
                return oldItem.emojiText.endsWith(newItem.emojiText)
                        && oldItem.emojiRes == newItem.emojiRes;
            }
        });
    }

    /**
     * 添加item点击事件
     */
    public void setOnItemClickListner(OnItemClickListener itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public EmojiRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmojiView imageView = new EmojiView(parent.getContext());
        return new EmojiRecyclerView.ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiRecyclerView.ViewHolder holder, int position) {
        final Emoji emoji = getItem(position);
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListner != null) {
                itemClickListner.onItemClick(emoji);
            }
        });
        ((EmojiView) holder.itemView).setImageResource(emoji.emojiRes);
    }

    public interface OnItemClickListener {
        void onItemClick(Emoji emoji);
    }

}
