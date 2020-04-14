package com.yu.hu.retrofittest.network;

/**
 * @author Hy
 * created on 2020/04/14 15:17
 **/
@SuppressWarnings({"WeakerAccess", "unused"})
public class ApiService {

    /**
     * 简化{@link retrofit2.Retrofit#create(Class)}方法
     */
    public static <T> T create(final Class<T> service) {
        return create(RetrofitManager.getsBaseUrl(),service);
    }

    public static <T> T create(String baseUrl,final Class<T> service) {
        return RetrofitManager.get(baseUrl).create(service);
    }
}
