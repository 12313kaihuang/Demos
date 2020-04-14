package com.yu.hu.retrofittest.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Hy
 * created on 2020/04/14 14:55
 * <p>
 * Retrofit管理
 * <p>
 * 如果想要使用{@link #get()}方法获取默认的Retrofit对象，
 * 必须通过{@link #init(String)}方法来设置一个baseUrl
 **/
@SuppressWarnings({"unused", "WeakerAccess"})
public class RetrofitManager {

    //服务器请求baseUrl
    private static String sBaseUrl = null;

    //超时时长 默认10s
    private static int sConnectTimeout = 10;

    //用于存储retrofit
    private static ConcurrentHashMap<String, Retrofit> sRetrofitMap;

    private static OkHttpClient.Builder sOkHttpBuilder;

    //用于日期转换
    private static Gson sDataFormat;

    static {
        sRetrofitMap = new ConcurrentHashMap<>();

        sOkHttpBuilder = new OkHttpClient.Builder();
        //超时时长
        sOkHttpBuilder.connectTimeout(sConnectTimeout, TimeUnit.SECONDS);

        sDataFormat = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    //初始化主要的baseUrl  便于后期直接获取
    public static void init(String baseUrl) {
        sBaseUrl = baseUrl;
    }

    public static String getsBaseUrl() {
        return sBaseUrl;
    }

    public static void setConnectTimeout(int connectTimeout) {
        sConnectTimeout = connectTimeout;
    }

    public static Retrofit get() {
        Retrofit retrofit = sRetrofitMap.get(sBaseUrl);
        if (retrofit == null) {
            throw new RuntimeException("BASE_URL为空");
        }
        return retrofit;
    }

    public static Retrofit get(String baseUrl) {
        Retrofit retrofit = sRetrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = createRetrofit(baseUrl);
            sRetrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    /**
     * 创建Retrofit对象
     *
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpBuilder.build())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(sDataFormat))
                .build();
    }
}
