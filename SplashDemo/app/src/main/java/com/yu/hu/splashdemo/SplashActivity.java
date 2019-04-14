package com.yu.hu.splashdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 项目名：SplashDemo
 * 包名：  com.yu.hu.splashdemo
 * 文件名：SplashActivity
 * 创建者：HY
 * 创建时间：2019/4/14 10:37
 * 描述：  闪屏页
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //延迟两秒进入主页
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }


    //禁用返回键
    @Override
    public void onBackPressed() {

    }
}
