package com.yu.hu.animatortest;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：AnimatorTest
 * 包名：  com.yu.hu.animatortest
 * 文件名：SecondActivity
 * 创建者：HY
 * 创建时间：2019/4/15 10:48
 * 描述：  属性动画监听
 */

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] res = {R.id.img_a, R.id.img_b, R.id.img_c, R.id.img_d, R.id.img_e,
            R.id.img_f, R.id.img_g, R.id.img_h};
    private List<ImageView> imageViewList = new ArrayList<>();
    private boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //显示返回键
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);

            for (int re : res) {
                ImageView imageView = findViewById(re);
                imageView.setOnClickListener(this);
                imageViewList.add(imageView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_a:
                if (flag = !flag) {
                    closeAnim();
                } else {
                    startAnim();
                }
                break;
            default:
                break;
        }
    }

    private void closeAnim() {
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY", i * 150, 0);
            animator.setDuration(500).start();
        }
    }

    private void startAnim() {
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY", 0, i * 150);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(500).start();
        }
    }
}
