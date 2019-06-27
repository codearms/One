package com.codearms.maoqiqi.one.news.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.NewsBean;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 14:39
 */
public interface NewsListContract {

    interface Presenter extends BasePresenter<View> {

        void getLatestNews();
    }

    interface View extends BaseView<Presenter> {

        void setLatestNews(NewsBean newsBean);
    }
}