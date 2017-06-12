package com.vienan.retrofitdemo.compose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vienan.retrofitdemo.R;
import com.vienan.retrofitdemo.compose.data.Response;
import com.vienan.retrofitdemo.compose.data.User;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/6/12.
 */

public class ComposeSampleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = (TextView) findViewById(R.id.text);

        mockServer().subscribe(new Action1<User>() {
            @Override
            public void call(User user) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private Observable<User> mockServer() {
        User mockUser = new User("mock_name");
        Response<User> mockResp = new Response<>(1, mockUser, "登录失败");
        return Observable.just(mockResp)
                .flatMap(new OriginDataFunc<User>())
                .compose(RxUtil.<User>normalSchedulers());
    }

    private class OriginDataFunc<T> implements Func1<Response<T>,Observable<T>> {


        @Override
        public Observable<T> call(Response<T> tResponse) {
            if (tResponse.getCode() != 0) {
                return Observable.error(new Throwable(tResponse.getMessage()));
            }
            return Observable.just(tResponse.getData());
        }
    }

}
