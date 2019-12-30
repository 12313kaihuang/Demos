package com.yu.hu.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;

import com.yu.hu.emoji.entity.Emoji;

import java.util.List;

/**
 * Created by Hy on 2019/12/30 16:06
 * <p>
 * 用于放表情的GridLayout
 * 默认一行7个表情
 **/
public class EmojiGridLayout extends GridLayout {

    private static final int DEFUALT_COLUMN = 7;  //默认一行7个表情

    private List<Emoji> emojiList;

    private Context mContext;

    public EmojiGridLayout(Context context) {
        this(context, null);
    }

    public EmojiGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setColumnCount(DEFUALT_COLUMN);
        int padding = dip2px(8);
        setPadding(padding, padding, padding, padding);
    }

    public void addEmojis(List<Emoji> emojiList) {
        this.emojiList = emojiList;
        LayoutParams params = new LayoutParams();
        params.width = 0;
        params.height = 0;

        for (int i = 0; i < emojiList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            params.columnSpec = GridLayout.spec(i % DEFUALT_COLUMN, 1f);
            params.rowSpec = GridLayout.spec(i / DEFUALT_COLUMN, 1f);
            imageView.setImageResource(emojiList.get(i).emojiRes);
            addView(imageView);
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
