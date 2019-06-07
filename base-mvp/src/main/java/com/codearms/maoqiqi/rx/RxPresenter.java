package com.codearms.maoqiqi.rx;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;

import io.reactivex.disposables.Disposable;

public interface RxPresenter<T extends BaseView> extends BasePresenter<T> {

    void addSubscribe(Disposable disposable);
}