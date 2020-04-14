package com.yu.hu.retrofittest.network;

/**
 * @author Hy
 * created on 2020/04/14 16:14
 * <p>
 * 请求返回结果
 **/
public class ApiResponse<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;

    public int code; //状态码
    public String msg; //信息
    public T data; //数据

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
