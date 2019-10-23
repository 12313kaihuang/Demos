package com.example.notificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notificationtest.entity.Music;
import com.example.notificationtest.service.MusicPlayerService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvName;
    private ImageView mIvIcon;
    private ImageView mIvPre;
    private ImageView mIvStart;
    private ImageView mIvNext;

    private boolean isPlaying = false;

    private BroadcastReceiver mMusicBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            Music music = intent.getParcelableExtra(MusicPlayerService.EXTRA_KEY_MUSIC);

            if (action.equals(MusicPlayerService.ACTION_PLAY)) {
                isPlaying = true;
                mIvStart.setImageResource(R.drawable.ic_playing);
                if (music == null) return;
                mTvName.setText(music.getName());
                mIvIcon.setImageResource(getResources().getIdentifier(music.getIcon(), "drawable", getPackageName()));
            } else if (action.equals(MusicPlayerService.ACTION_PAUSE)) {
                isPlaying = false;
                mIvStart.setImageResource(R.drawable.ic_pause);
            }

            //上一首/下一首
            if ((action.equals(MusicPlayerService.ACTION_NEXT) || action.equals(MusicPlayerService.ACTION_PRE))
                    && music != null) {
                isPlaying = true;
                mIvStart.setImageResource(R.drawable.ic_playing);
                mTvName.setText(music.getName());
                mIvIcon.setImageResource(getResources().getIdentifier(music.getIcon(), "drawable", getPackageName()));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMusicBroadcastReceiver);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicPlayerService.ACTION_PLAY);
        intentFilter.addAction(MusicPlayerService.ACTION_PLAY);
        intentFilter.addAction(MusicPlayerService.ACTION_PLAY);
        intentFilter.addAction(MusicPlayerService.ACTION_PLAY);
        registerReceiver(mMusicBroadcastReceiver, intentFilter);
    }

    private void initView() {
        mTvName = findViewById(R.id.tv_music_name);
        mIvIcon = findViewById(R.id.iv_music);
        mIvPre = findViewById(R.id.iv_pre);
        mIvStart = findViewById(R.id.iv_play);
        mIvNext = findViewById(R.id.iv_next);
    }

    private void initEvent() {
        mIvPre.setOnClickListener(this);
        mIvStart.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MusicPlayerService.class);
        switch (v.getId()) {
            case R.id.iv_play:
                if (isPlaying) {
                    mIvStart.setImageResource(R.drawable.ic_pause);
                    intent.setAction(MusicPlayerService.ACTION_PAUSE);
                } else {
                    intent.setAction(MusicPlayerService.ACTION_PLAY);
                    mIvStart.setImageResource(R.drawable.ic_playing);
                }
                isPlaying = !isPlaying;
                break;
            case R.id.iv_pre:
                intent.setAction(MusicPlayerService.ACTION_PRE);
                break;
            case R.id.iv_next:
                intent.setAction(MusicPlayerService.ACTION_NEXT);
                break;
            default:
                break;
        }
        startService(intent);
    }

    private void playMusic() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        if (isPlaying) {
            mIvStart.setImageResource(R.drawable.ic_pause);
            intent.setAction(MusicPlayerService.ACTION_PAUSE);
        } else {
            intent.setAction(MusicPlayerService.ACTION_PLAY);
            mIvStart.setImageResource(R.drawable.ic_playing);
        }
        isPlaying = !isPlaying;
        startService(intent);
    }


}
