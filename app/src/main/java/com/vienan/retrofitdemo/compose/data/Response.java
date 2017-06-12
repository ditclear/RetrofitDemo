package com.vienan.retrofitdemo.compose.data;

/**
 * 页面描述：数据基类
 *
 * Created by ditclear on 2017/6/12.
 */

public class Response<T> {

    private int code;
    private T data;
    private String message;

    public Response(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
