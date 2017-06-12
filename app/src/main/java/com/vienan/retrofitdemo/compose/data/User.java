package com.vienan.retrofitdemo.compose.data;

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/6/12.
 */

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
