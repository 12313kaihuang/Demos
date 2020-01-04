package com.yu.hu.emoji.widget;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.yu.hu.emoji.EmojiManager;
import com.yu.hu.emoji.entity.Emoji;

import java.util.regex.Matcher;

/**
 * Created by Hy on 2019/12/28 14:25
 * <p>
 * 可显示表情的EditText
 **/
public class EmojiEditText extends AppCompatEditText implements IEmojiable {

    private static final String TAG = "EmojiEditText";

    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addEmoji(Emoji emoji) {
        if (!hasFocus()) {
            return;
        }
        Log.d(TAG, String.format("addEmoji: emoji = %s", emoji.toString()));
        //获取当前光标位置
        int start = getSelectionStart();
        Editable currentText = getText();
        if (currentText == null) {
            setEmojiText(emoji.emojiText);
            setSelection(emoji.emojiText.length());
            return;
        }

        int selection; //标记添加表情后的光标位置
        //直接操作currentText会触发onTextChanged
        StringBuilder builder = new StringBuilder(currentText.toString());
        if (start >= 0) {
            builder.insert(start, emoji.emojiText);
            selection = start + emoji.emojiText.length();
        } else {
            builder.append(emoji.emojiText);
            selection = currentText.length();
        }
        setEmojiText(builder.toString(), selection);
    }

    @NonNull
    @Override
    public String getEmojiText() {
        Editable text = getText();
        return text == null ? "" : text.toString();
    }

    @Override
    public void setEmojiText(String str) {
        setEmojiText(str, -1);
    }

    /**
     * @param selection 光标位置  -1表示不特殊设置
     */
    public void setEmojiText(String str, int selection) {
        Log.d(TAG, String.format("setEmojiText: str = %s,selection = %d", str, selection));
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

        if (selection != -1) {
            setSelection(selection);
        }
    }

    @CallSuper
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (lengthBefore == 0 && lengthAfter >= 1) {
            Log.d(TAG, String.format("onTextChanged: 输入 text = %s,selection = %d", text, getSelectionStart()));
            setEmojiText(text.toString(), getSelectionStart());
        } else if (lengthBefore >= 1 && lengthAfter == 0) {
            Log.d(TAG, String.format("onTextChanged: 删除 text = %s,selection = %d", text, getSelectionStart()));
            setEmojiText(text.toString(), getSelectionStart());
        }
    }


}
