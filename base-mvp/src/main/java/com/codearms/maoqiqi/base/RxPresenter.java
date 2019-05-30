package com.codearms.maoqiqi.base;

import io.reactivex.disposables.Disposable;

public interface RxPresenter<T extends BaseView> extends BasePresenter<T> {

    void addSubscribe(Disposable disposable);
}