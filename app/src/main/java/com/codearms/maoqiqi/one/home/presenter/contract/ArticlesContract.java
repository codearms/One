package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;
import com.codearms.maoqiqi.one.home.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.home.data.bean.ArticleBeans;

import java.util.List;

public interface ArticlesContract {

    interface Presenter extends BasePresenter {

        void getHomeArticles();

        void getWxArticles(int id, int page);

        void getKnowledgeArticles(int page, int cid);

        void getProjectArticles(int page, int cid);
    }

    interface View extends BaseView<Presenter> {

        void setHomeArticles(List<ArticleBean> topArticleBeans, ArticleBeans articleBeans);

        void setArticles(ArticleBeans articleBeans);
    }
}