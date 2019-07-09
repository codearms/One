package com.codearms.maoqiqi.one.gank.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:36
 */
public interface HeaderImageContract {

    interface Presenter extends BasePresenter<View> {

        void getHeaderImage(boolean isRandom);
    }

    interface View extends BaseView<Presenter> {

        void setHeaderImage(String url);
    }
}