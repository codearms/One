package com.codearms.maoqiqi.one.news.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.NewsBean;

public interface NewsListContract {

    interface Presenter extends BasePresenter<View> {

        void getLatestNews(boolean isRefresh);
    }

    interface View extends BaseView<Presenter> {

        void setLatestNews(NewsBean newsBean, boolean isRefresh);
    }
}