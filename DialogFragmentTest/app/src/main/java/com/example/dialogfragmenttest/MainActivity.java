package com.example.dialogfragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.dialogfragmenttest.databinding.ActivityMainBinding;
import com.example.dialogfragmenttest.dialog.SimpleDialog;
import com.example.dialogfragmenttest.dialog.SimpleDialog2;
import com.example.dialogfragmenttest.dialog.UpdateDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.simpleDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                new SimpleDialog().show(getSupportFragmentManager(), "1");
            }
        });

        binding.simpleDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                new SimpleDialog2().show(getSupportFragmentManager(), "2");
            }
        });

        binding.simpleDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                new UpdateDialog().show(getSupportFragmentManager(), "3");
            }
        });
    }
}
