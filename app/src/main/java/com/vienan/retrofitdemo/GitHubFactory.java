package com.vienan.retrofitdemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 2017/2/9.
 */

public class GitHubFactory {

    private static Retrofit mInstance;
    public static Retrofit getInstance() {
        if (mInstance == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic Z3Vlc3Q6Z3Vlc3Q=").build();
                            return chain.proceed(request);
                        }
                    });
            mInstance = new Retrofit.Builder()
//                    .baseUrl("http://172.18.1.54:1005/cvt-store/rest/")
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .client(httpClientBuilder.build())
                    .build();
        }
        return mInstance;
    }

}
