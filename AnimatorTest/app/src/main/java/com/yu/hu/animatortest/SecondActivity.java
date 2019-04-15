package com.yu.hu.animatortest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 项目名：AnimatorTest
 * 包名：  com.yu.hu.animatortest
 * 文件名：SecondActivity
 * 创建者：HY
 * 创建时间：2019/4/15 10:48
 * 描述：  属性动画监听
 */

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }


    public void click(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha", 0f, 1f);
        animator.setDuration(1000);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Toast.makeText(SecondActivity.this,"动画结束",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(SecondActivity.this,"动画结束",Toast.LENGTH_SHORT).show();
            }
        });
        animator.start();
    }
}
