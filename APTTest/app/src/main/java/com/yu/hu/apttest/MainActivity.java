package com.yu.hu.apttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yu.hu.libannotation.RandomInt;
import com.yu.hu.libannotation.RandomString;
import com.yu.hu.libannotation.RandomUtils;

public class MainActivity extends AppCompatActivity {

    @RandomInt(minValue = 50)
    int random;

    @RandomString(length = 14)
    String randomStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RandomUtils.inject(this);
//        new MainActivity_Random(this);
        String result = String.format("random = %d, randomStr = %s", random, randomStr);
        Log.d("MainActivity", "onCreate: random=" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}