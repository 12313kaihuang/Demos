package com.yu.hu.emoji.widget;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.yu.hu.emoji.EmojiManager;

import java.util.regex.Matcher;

/**
 * Created by Hy on 2019/12/28 14:25
 * <p>
 * 可显示表情的EditText
 **/
public class EmojiEditText extends AppCompatEditText implements IEmojiable {

    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addEmoji(@DrawableRes int emojiRes) {
        String emojiText = EmojiManager.getEmojiText(emojiRes);
        setEmojiText(getEmojiText() + emojiText);
    }

    @NonNull
    @Override
    public String getEmojiText() {
        Editable text = getText();
        return text == null ? "" : text.toString();
    }

    @Override
    public void setEmojiText(String str) {
        if (TextUtils.isEmpty(str)) {
            setText(str);
        }

        Matcher m = EmojiManager.matcher(str);
        SpannableString spannableString = new SpannableString(str);
        while (m.find()) {
            int emojiRes = EmojiManager.getEmojiRes(m.group());
            ImageSpan imageSpan = new ImageSpan(getContext(), emojiRes);
            spannableString.setSpan(imageSpan, m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
    }

}
