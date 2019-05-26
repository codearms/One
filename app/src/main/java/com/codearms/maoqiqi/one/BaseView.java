package com.codearms.maoqiqi.one;

public interface BaseView<T> {

    void setPresenter(T presenter);

    boolean isActive();
}