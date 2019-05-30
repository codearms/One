package com.codearms.maoqiqi.base;

public abstract class PresenterImpl<T extends BaseView> implements BasePresenter<T> {

    protected T view;

    public PresenterImpl(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}