package com.yu.hu.emoji.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yu.hu.emoji.dao.EmojiDao;
import com.yu.hu.emoji.db.EmojiDatabase;
import com.yu.hu.emoji.entity.Emoji;

import java.util.List;


/**
 * Created by Hy on 2019/12/28 18:26
 **/
public class EmojiRepository {

    private static final String TAG = "EmojiRepository";

    private EmojiDao mEmojiDao;

    private static volatile EmojiRepository sINSTANCE;

    public static EmojiRepository getInstance(Application application) {
        if (sINSTANCE == null) {
            synchronized (EmojiRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new EmojiRepository(application);
                }
            }
        }
        return sINSTANCE;
    }

    private EmojiRepository(Application application) {
        Log.d(TAG, "EmojiRepository: init");
        EmojiDatabase database = EmojiDatabase.getDatabase(application);
        mEmojiDao = database.emojiDao();
    }

    public List<Emoji> queryAllByType(@Emoji.EMOJITYPE int type) {
        return mEmojiDao.queryAllByType(type);
    }

    public int getEmojiRes(String emojiText) {
        Emoji emoji = mEmojiDao.getEmojiByText(emojiText);
        return emoji != null ? emoji.emojiRes : 0;
    }

    @Nullable
    public String getEmojiText(int emojiRes) {
        Emoji emoji = mEmojiDao.getEmojiByRes(emojiRes);
        return emoji != null ? emoji.emojiText : null;
    }

    @NonNull
    public List<Emoji> getRecentEmoji(int num) {
        return mEmojiDao.queryRecent(num);
    }

    public void insertAll(Emoji... emojis) {
        mEmojiDao.insertAll(emojis);
    }


}
