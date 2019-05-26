package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;

import java.util.List;

public interface ArticlesContract {

    interface Presenter extends BasePresenter {

        void getHomeArticles();

        void getWxNumberData(int id, int page);
    }

    interface View extends BaseView<Presenter> {

        void setHomeArticles(List<ArticleBeans.ItemArticleBean> topArticleBeans, ArticleBeans articleBeans);

        void setWxNumberData(ArticleBeans articleBeans);
    }
}