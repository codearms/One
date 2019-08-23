package com.codearms.maoqiqi.one.news.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;

public interface NewsDetailContract {

    interface Presenter extends BasePresenter<View> {

        void getNewsDetail(int id);
    }

    interface View extends BaseView<Presenter> {

        void setNewsDetail(NewsDetailBean newsDetailBean);
    }
}