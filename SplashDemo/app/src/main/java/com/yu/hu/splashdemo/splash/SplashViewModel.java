package com.yu.hu.splashdemo.splash;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.yu.hu.splashdemo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * create by hy on 2019/11/29 21:36
 *
 * @see SplashActivity
 */
public class SplashViewModel extends AndroidViewModel {

    /**
     * 倒计时3s
     */
    private static final long SPLASH_TIME_SECOND = 2L;

    private Disposable subscribe;
    private Context mContext;

    private MutableLiveData<Long> mRestTime;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        mRestTime = new MutableLiveData<>(SPLASH_TIME_SECOND + 1);
    }

    public MutableLiveData<Long> getRestTime() {
        return mRestTime;
    }

    public String translate2String(Long restTime) {
        String time = mContext.getString(R.string.skip_with_seconds);
        return String.format(time, restTime);
    }

    /**
     * 开始倒计时
     */
    public void startInterval() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(SPLASH_TIME_SECOND + 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(now -> {
                    Log.d("test", "startInterval: now = "+(SPLASH_TIME_SECOND - now));
                    mRestTime.postValue(SPLASH_TIME_SECOND - now);
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }
}
