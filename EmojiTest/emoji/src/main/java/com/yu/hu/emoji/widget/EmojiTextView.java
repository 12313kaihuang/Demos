package com.yu.hu.emoji.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.yu.hu.emoji.EmojiManager;

import java.util.regex.Matcher;

/**
 * Created by Hy on 2019/12/27 18:40
 * <p>
 * 可显示表情的TextView
 **/
public class EmojiTextView extends AppCompatTextView implements IEmojiable {

    public EmojiTextView(Context context) {
        this(context, null);
    }

    public EmojiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getEmojiText() {
        CharSequence text = getText();
        return text == null ? "" : text.toString();
    }

    @Override
    public void setEmojiText(String str) {
        if (TextUtils.isEmpty(str)) {
            setText(str);
            return;
        }

        Matcher m = EmojiManager.matcher(str);
        SpannableString spannableString = new SpannableString(str);
        while (m.find()) {
            int emojiRes = EmojiManager.getEmojiRes(m.group());
            ImageSpan imageSpan = new ImageSpan(getContext(), emojiRes);
            spannableString.setSpan(imageSpan, m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            System.out.println(m.group() + m.start() + m.end());
        }
        setText(spannableString);
    }
}
