package com.yu.hu.retrofittest;

import androidx.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Hy
 * created on 2020/04/14 14:25
 **/
public interface SplashService {

    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    Call<BingImg> getBingImg();

    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    LiveData<BingImg> getBingImgLiveData();
}
