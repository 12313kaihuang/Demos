package com.yu.hu.emoji.widget;

import androidx.annotation.NonNull;

/**
 * Created by Hy on 2019/12/28 15:19
 * <p>
 * 实现带有Emoji表情所需要具备的一些通用能力
 **/
public interface IEmojiable {

    /**
     * 获取当前文本内容
     */
    @NonNull
    String getCurrentText();

    /**
     * 设置带有表情的文本
     */
    void setEmojiText(String str);
}
