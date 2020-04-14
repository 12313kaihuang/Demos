package com.yu.hu.retrofittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yu.hu.retrofittest.network.ApiService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        ApiService.create("https://cn.bing.com/", SplashService.class)
                .getBingImgLiveData().observe(this, new Observer<BingImg>() {
            @Override
            public void onChanged(BingImg bingImg) {
                TextView textVIew = findViewById(R.id.text_view);
                textVIew.setText(new Gson().toJson(bingImg));
            }
        });
    }
}
