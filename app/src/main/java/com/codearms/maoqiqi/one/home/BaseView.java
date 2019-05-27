package com.codearms.maoqiqi.one.home;

public interface BaseView<T> {

    void setPresenter(T presenter);

    boolean isActive();
}