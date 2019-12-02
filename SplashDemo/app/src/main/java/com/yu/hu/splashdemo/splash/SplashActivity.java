package com.yu.hu.splashdemo.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.yu.hu.splashdemo.MainActivity;
import com.yu.hu.splashdemo.R;
import com.yu.hu.splashdemo.databinding.ActivitySplashBinding;

/**
 * create by hy on 2019/12/01 14:48
 */
public class SplashActivity extends AppCompatActivity {

    private SplashViewModel mViewModel;

    //剩余时间的监听
    private Observer<Long> resetTimeObserver = resetTime -> {
        if (resetTime == 0) {
            toMainActivity();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding mDataBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        mDataBinding.setViewModel(mViewModel);

        mDataBinding.tvSkip.setOnClickListener(v -> {
            //移除监听 否则剩余1s时点击跳过会出现两次跳转至MainActivity情况
            mViewModel.getRestTime().removeObserver(resetTimeObserver);
            toMainActivity();
        });

        mViewModel.getRestTime().observe(this, resetTimeObserver);
    }

    /**
     * 跳转至MainActivity
     */
    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //singTask
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.startInterval();
    }

    @Override
    public void onBackPressed() {
        //屏蔽back键
    }
}
