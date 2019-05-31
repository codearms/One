package com.codearms.maoqiqi.one.utils;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;

import io.reactivex.observers.ResourceObserver;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private BaseView<? extends BasePresenter> view;

    public BaseObserver() {
    }

    public BaseObserver(BaseView<? extends BasePresenter> view) {
        this.view = view;
    }

    @Override
    public void onError(Throwable e) {
        if (view == null) return;

        view.showErrorMsg(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}