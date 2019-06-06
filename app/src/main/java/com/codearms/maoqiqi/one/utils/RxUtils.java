package com.codearms.maoqiqi.one.utils;

import com.codearms.maoqiqi.one.data.bean.CommonBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<CommonBean<T>, T> handleResult() {
        return observable -> observable.flatMap((Function<CommonBean<T>, ObservableSource<T>>) commonBean -> {
            if (commonBean.getErrorCode() == 0) {
                return createData(commonBean.getData());
            } else {
                return Observable.error(new ErrorCodeException(commonBean.getErrorCode(), commonBean.getErrorMsg()));
            }
        });
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(emitter -> {
            try {
                if (!emitter.isDisposed()) {
                    // 数据不为空才发,为空直接发onComplete()
                    if (t != null) {
                        emitter.onNext(t);
                    }
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}