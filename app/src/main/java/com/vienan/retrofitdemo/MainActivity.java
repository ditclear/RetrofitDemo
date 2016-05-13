package com.vienan.retrofitdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        /*if (AppUtils.isNetworkReachable(getApplicationContext())){
            Toast.makeText(MainActivity.this,"连接网络", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this,"无法连接网络",Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.text);
        final ProgressDialog dialog=ProgressDialog.show(this,"正在加载","请稍候...");
        //添加缓存，没网时加载缓存数据
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient().newBuilder();
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "responses");

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
               /* if (!AppUtils.isNetworkReachable(MyApp.getContext())) {
                    *//*request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_C ACHE)
                            .url("https://publicobject.com/helloworld.txt").build();*//*
                    Toast.makeText(MainActivity.this,"暂无网络",Toast.LENGTH_SHORT).show();//子线程安全显示Toast
                }else {
                    Toast.makeText(MainActivity.this,"网络良好",Toast.LENGTH_SHORT).show();
                }*/

                Response response = chain.proceed(request);
                /*if (AppUtils.isNetworkReachable(MyApp.getContext())) {
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }*/
                Log.d("body",response.request().url().toString());
                return response;
            }
        };
        okHttpBuilder.cache()
        okHttpBuilder.addInterceptor(interceptor);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHub gitHub=retrofit.create(GitHub.class);

        gitHub.contributors("square","retrofit")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Contributor>>() {

                    String name="";
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                        text.setText(name);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        for (Contributor contributor:contributors){
                            Log.d("c",contributor.login);
                            name+=contributor.login+"\n";
                        }
                    }
                });
    }

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

    public interface GitHub {
        @GET("repos/{owner}/{repo}/contributors")
        Observable<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
    }
}
