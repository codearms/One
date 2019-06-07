package com.codearms.maoqiqi.base;

public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    protected T view;

    public BasePresenterImpl(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}