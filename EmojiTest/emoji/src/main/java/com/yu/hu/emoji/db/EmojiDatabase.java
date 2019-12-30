package com.yu.hu.emoji.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yu.hu.emoji.EmojiManager;
import com.yu.hu.emoji.converter.DateConverter;
import com.yu.hu.emoji.dao.EmojiDao;
import com.yu.hu.emoji.entity.Emoji;

/**
 * Created by Hy on 2019/12/28 18:22
 **/
@Database(entities = {Emoji.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class EmojiDatabase extends RoomDatabase {

    private static final String TAG = "EmojiDatabase";

    public abstract EmojiDao emojiDao();

    //设置为单例，防止同时打开多个数据库实例
    private static volatile EmojiDatabase INSTANCE;

    //最后在application中初始化一次
    public static EmojiDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EmojiDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "getDatabase: init");
                    //创建一个对象EmojiDatabase并将其命名"emoji_database"
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EmojiDatabase.class, "emoji_database")
                            .allowMainThreadQueries()  //允许在主线程进行查询操作
                            .addCallback(sRoomDatabaseCallback)  //需要执行一次数据库操作 如查询操作才会触发？
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //初始化时插入数据
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        //Called when the database is created for the first time. This is called after all the tables are created.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "initDatabase insert emojis");
            new InitTask(INSTANCE).execute();
        }
    };

    //用于初始化插入表情
    private static class InitTask extends AsyncTask<Void, Void, Void> {

        private final EmojiDao mDao;

        InitTask(EmojiDatabase db) {
            this.mDao = db.emojiDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insertAll(EmojiManager.getAllEmojis());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: 数据库初始化完成");
        }
    }
}
