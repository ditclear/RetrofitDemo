package com.vienan.retrofitdemo.compose;

import com.vienan.retrofitdemo.compose.data.Response;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/6/12.
 */

public class RxUtil {

    private RxUtil() {
    }

    public static <T> Observable.Transformer<T,T> normalSchedulers() {
        return new Observable.Transformer<T,T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(
                        AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<Response<T>,T> getOriginData() {
        return new Observable.Transformer<Response<T>,T>() {
            @Override
            public Observable<T> call(Observable<Response<T>> responseObservable) {

                return responseObservable.flatMap(new Func1<Response<T>,Observable<T>>() {
                    @Override
                    public Observable<T> call(Response<T> tResponse) {
                        if (tResponse.getCode() != 0) {
                            return Observable.error(new Throwable(tResponse.getMessage()));
                        }
                        return Observable.just(tResponse.getData());
                    }
                });
            }
        };
    }

}
