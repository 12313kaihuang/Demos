package com.yu.hu.emoji.entity;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by Hy on 2019/12/28 15:13
 * <p>
 * 表情实体类
 **/
@Entity(tableName = "tb_emoji")
public class Emoji {

    public static final int TYPE_QQ = 1;
    public static final int TYPE_EMOJI = 2;

    @IntDef({TYPE_QQ, TYPE_EMOJI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EMOJITYPE {

    }

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "emoji_text")
    public String emojiText;  //对应的文字

    @ColumnInfo(name = "emoji_res")
    public int emojiRes;  //资源id

    @EMOJITYPE
    @ColumnInfo(name = "type")
    public int type;  //类型 qq = 1，emoji = 2

    @ColumnInfo(name = "desc")
    private String desc; //描述

    @Nullable
    @ColumnInfo(name = "recent_time")
    public Date recent;  //最近一次使用时间

    public Emoji(@NonNull String emojiText, int emojiRes, String desc, @EMOJITYPE int type) {
        this.emojiText = emojiText;
        this.emojiRes = emojiRes;
        this.desc = desc;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }
}
