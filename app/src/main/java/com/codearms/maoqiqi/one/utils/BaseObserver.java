package com.codearms.maoqiqi.one.utils;

import io.reactivex.observers.ResourceObserver;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}