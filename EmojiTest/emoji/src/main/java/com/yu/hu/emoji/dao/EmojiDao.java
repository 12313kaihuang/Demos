package com.yu.hu.emoji.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yu.hu.emoji.entity.Emoji;

import java.util.List;

/**
 * Created by Hy on 2019/12/28 18:20
 * <p>
 * 指向数据表的相关操作
 **/
@Dao
public interface EmojiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Emoji... emojis);

    @Query("SELECT * FROM TB_EMOJI WHERE type = :type ORDER BY id")
    List<Emoji> queryAllByType(int type);

    @Query("SELECT * FROM TB_EMOJI WHERE recent_time != 0 ORDER BY recent_time DESC LIMIT :num")
    List<Emoji> queryRecent(int num);

    @Query("SELECT * FROM TB_EMOJI WHERE emoji_text = :emojiText LIMIT 1")
    Emoji getEmojiByText(String emojiText);

    @Query("SELECT * FROM TB_EMOJI WHERE emoji_res = :emojiRes LIMIT 1")
    Emoji getEmojiByRes(int emojiRes);

}
