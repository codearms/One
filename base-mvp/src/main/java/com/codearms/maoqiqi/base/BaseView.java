package com.codearms.maoqiqi.base;

public interface BaseView<T extends BasePresenter> {

    /**
     * 设置presenter
     *
     * @param presenter presenter
     */
    void setPresenter(T presenter);

    /**
     * Fragment是否是活跃的
     *
     * @return true:是 false:不是
     */
    boolean isActive();

    /**
     * 显示加载视图
     */
    void showLoading();

    /**
     * 显示错误信息
     *
     * @param errorMsg 错误信息
     */
    void showErrorMsg(String errorMsg);
}