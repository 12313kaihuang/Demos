package com.yu.hu.animatortest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

        ImageView imageView = findViewById(R.id.img);
        switch (v.getId()) {
            case R.id.img:
                Toast.makeText(this, "move", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_move:
                //异步实现（同时进行）
                ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f).setDuration(1000).start();
                ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f).setDuration(1000).start();
                ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f).setDuration(1000).start();
                break;
            case R.id.btn_move1:
                //上面方式的优化 更加节省资源
                PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation", 0, 360F);
                PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationX", 0, 200F);
                PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("translationY", 0, 200F);
                ObjectAnimator.ofPropertyValuesHolder(imageView, p1, p2, p3).setDuration(1000).start();
                break;
            case R.id.btn_move2:
                //第三种方法
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f);
                AnimatorSet set = new AnimatorSet();
                //顺序执行
                // set.playSequentially(animator1,animator2,animator3);
                //并发执行
                set.playTogether(animator1, animator2, animator3);
                set.setDuration(1000).start();
                break;
            case R.id.btn_move3:
                //先平移，再旋转
                animator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
                animator2 = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f);
                animator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f);
                set = new AnimatorSet();
                set.play(animator2).with(animator3);
                set.play(animator1).after(animator2);
                set.setDuration(1000).start();
                break;
            case R.id.btn_toDemo:
                startActivity(new Intent(MainActivity.this, DemoActivity.class));
                break;
        }
    }
}
