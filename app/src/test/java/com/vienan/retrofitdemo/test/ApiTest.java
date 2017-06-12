package com.vienan.retrofitdemo.test;

import com.vienan.retrofitdemo.GitHub;
import com.vienan.retrofitdemo.GitHubFactory;

import org.junit.Test;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 2017/2/9.
 */
public class ApiTest {


    @Test
    public void mockServerTest() throws Exception {
        // 让Schedulers.io()返回当前线程
//        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook(){
//            @Override
//            public Scheduler getIOScheduler() {
//                return Schedulers.immediate();
//            }
//
//        });
//        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        });
//        //创建mock服务
//        MockWebServer server=new MockWebServer();
//
//        //2.添加预置的响应，响应会按照先进先出的顺序依次返回
//        server.enqueue(new MockResponse().setBody("i am ditclear"));
//        server.enqueue(new MockResponse().setResponseCode(404).setBody("not found"));
//        server.enqueue(new MockResponse().setResponseCode(503).setBody("Service Unavailible"));
//        final CountDownLatch latch=new CountDownLatch(1);
//        GitHubFactory.getInstance().create(GitHub.class)
//                .contributors("square", "retrofit")
//                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<? extends Object>>() {
//                    @Override
//                    public void call(List<? extends Object> objects) {
//                        assertThat(objects).isInstanceOf(String.class);
//                        latch.countDown();
//                    }
//                });
//        latch.await();
        Object l=GitHubFactory.getInstance().create(GitHub.class)
                .contributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toBlocking().last();
        assertThat(l).isInstanceOf(String .class);
    }
}
