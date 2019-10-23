package com.example.notificationtest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.notificationtest.entity.Music;

import java.util.List;

/**
 * @author hy
 * @Date 2019/10/19 0019
 **/
public class MusicPlayerService extends Service {

    private static final String TAG = MusicPlayerService.class.getName();

    public static final String ACTION_PLAY = TAG + ".action.playing";
    public static final String ACTION_PAUSE = TAG + ".action.pause";
    public static final String ACTION_PRE = TAG + ".action.pre";
    public static final String ACTION_NEXT = TAG + ".action.next";

    public static final String EXTRA_KEY_MUSIC = "key_music";

    private List<Music> mMusicList;
    private MediaPlayer mMediaPlayer;

    private int mCurrentPosition;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mMusicList = Music.getDefaultData();
        this.mCurrentPosition = 0;
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(getBaseContext(), mMusicList.get(mCurrentPosition).getResId());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        disposeAction(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void disposeAction(Intent intent) {
        String action = intent.getAction();
        if (action == null) return;

        if (action.equals(ACTION_PLAY)) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        } else if (action.equals(ACTION_PAUSE)) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }


        //上一首或下一首
        closeMediaPlayer();
        if (action.equals(ACTION_PRE)) {
            mCurrentPosition = (mCurrentPosition + 1) % mMusicList.size();
        } else if (action.equals(ACTION_NEXT)) {
            mCurrentPosition = Math.abs((mCurrentPosition - 1)) % mMusicList.size();
        }
        mMediaPlayer = MediaPlayer.create(getBaseContext(), mMusicList.get(mCurrentPosition).getResId());
        mMediaPlayer.start();

        sendBroadcast(new Intent(action));
    }

    //关闭 否则两个音频会一起播放
    private void closeMediaPlayer() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
    }
}
