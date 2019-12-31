package com.yu.hu.emoji.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hy on 2019/12/31 11:04
 * <p>
 * 底部间隔
 **/
public class EmojiDecoration extends RecyclerView.ItemDecoration {

    private int bottomOffset;  //dp

    public EmojiDecoration(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = TransformUtils.dip2px(view.getContext(), bottomOffset);
    }
}
