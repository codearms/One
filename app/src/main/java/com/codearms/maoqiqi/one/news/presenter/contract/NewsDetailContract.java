package com.codearms.maoqiqi.one.news.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 18:29
 */
public interface NewsDetailContract {

    interface Presenter extends BasePresenter<View> {

        void getNewsDetail(int id);
    }

    interface View extends BaseView<Presenter> {

        void setNewsDetail(NewsDetailBean newsDetailBean);
    }
}