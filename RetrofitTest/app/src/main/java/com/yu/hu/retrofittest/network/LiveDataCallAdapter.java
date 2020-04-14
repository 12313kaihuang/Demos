package com.yu.hu.retrofittest.network;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Hy
 * created on 2020/04/14 15:48
 * <p>
 * 自定义CallAdapter用于解析返回值为LiveData类型的请求
 * 如果不是{@link ApiResponse}类型的返回值，出现错误时会返回null
 **/
public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {

    private Type mResponseType;
    private boolean isApiResponse;

    LiveDataCallAdapter(Type mResponseType, boolean isApiResponse) {
        this.mResponseType = mResponseType;
        this.isApiResponse = isApiResponse;
    }

    @NotNull
    @Override
    public Type responseType() {
        return mResponseType;
    }

    @NotNull
    @Override
    public LiveData<T> adapt(@NotNull final Call<T> call) {
        return new MyLiveData<>(call, isApiResponse);
    }

    private static class MyLiveData<T> extends LiveData<T> {

        private AtomicBoolean stared = new AtomicBoolean(false);
        private final Call<T> call;
        private boolean isApiResponse;

        MyLiveData(Call<T> call, boolean isApiResponse) {
            this.call = call;
            this.isApiResponse = isApiResponse;
        }

        @Override
        protected void onActive() {
            super.onActive();
            //确保执行一次
            if (stared.compareAndSet(false, true)) {
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                        T body = response.body();
                        postValue(body);
                    }

                    @Override
                    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                        if (isApiResponse) {
                            //noinspection unchecked
                            postValue((T) new ApiResponse<>(ApiResponse.CODE_ERROR, t.getMessage()));
                        } else {
                            postValue(null);
                        }
                    }
                });
            }
        }
    }
}
