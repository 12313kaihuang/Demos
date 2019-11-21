package com.yu.hu.hideicontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.launch_app_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHideApp(v);
            }
        });
    }

    private void launchHideApp(View v) {
        String hidePkgName = "com.yu.hu.hideiconapp";

        if (isAppInstalled(hidePkgName)) {
            openActivity(hidePkgName);
            //openApp(hidePkgName);

        }else {
            Toast.makeText(this,"未安装",Toast.LENGTH_SHORT).show();
        }

    }

    //打开App  直接跳转到那个App中而不是仍停留在本App
    private void openApp(String hidePkgName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(hidePkgName);
        startActivity(intent);
    }

    //启动指定的Activity  但是当前还是在本App内
    private void openActivity(String hidePkgName) {
        Intent intent = new Intent();
        //这里 第一个参数是包名 第二个参数对应 包名.Activity名
        intent.setComponent(new ComponentName(hidePkgName, hidePkgName + ".MainActivity"));
        //sche.host   下面两句不要也是可以的
        Uri uri = Uri.parse("com.yu.hu.hideiconapp.AuthActivity");
        intent.setData(uri);
        startActivity(intent);
    }

    private boolean isAppInstalled( String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
