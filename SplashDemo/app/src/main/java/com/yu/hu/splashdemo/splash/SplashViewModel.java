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
    private SplashModel splashModel;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        mRestTime = new MutableLiveData<>(SPLASH_TIME_SECOND + 1);
        splashModel = new SplashModel();
    }

    public MutableLiveData<Long> getRestTime() {
        return mRestTime;
    }

    //可以直接在view中调用 viewModel.imgUrl
    public SplashModel getSplashModel() {
        return splashModel;
    }

    public String translate2String(Long restTime) {
        String time = mContext.getString(R.string.skip_with_seconds);
        return String.format(time, restTime);
    }

    /**
     * 开始倒计时
     */
    @SuppressWarnings("WeakerAccess")
    public void startInterval() {
        splashModel.setUrl("https://www.bing.com/th?id=OHR.MarrakechMarket_ZH-CN5880133555_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
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
