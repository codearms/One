package com.codearms.maoqiqi.base;

public interface BasePresenter<T extends BaseView> {

    void detachView();
}