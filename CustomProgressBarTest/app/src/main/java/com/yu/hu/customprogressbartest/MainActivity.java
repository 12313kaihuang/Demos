package com.yu.hu.customprogressbartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.yu.hu.customprogressbartest.progress.ProgressView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressView progressBar2;

    private Random random = new Random(System.currentTimeMillis());

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int progress = msg.what;
            if (progress <= 100) {
                progressBar.setProgress(progress);
                progressBar2.setProgress(progress);

                int i = random.nextInt(100);
                mHandler.sendEmptyMessageDelayed(progress + 1, i + 50);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        progressBar2 = findViewById(R.id.progress_view);


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        });
    }
}
