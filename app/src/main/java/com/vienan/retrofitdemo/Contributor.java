package com.vienan.retrofitdemo;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 2017/2/9.
 */

public class Contributor {
    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
