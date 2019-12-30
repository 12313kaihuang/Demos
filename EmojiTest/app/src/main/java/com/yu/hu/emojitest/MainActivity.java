package com.yu.hu.emojitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.yu.hu.emoji.test.EmojiTestActivity;
import com.yu.hu.emojitest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewDataBinding.emojiTestBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EmojiTestActivity.class)));
    }
}
