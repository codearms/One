package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;

import java.util.List;

public interface ArticlesContract {

    interface Presenter extends BasePresenter<View> {

        void getHomeArticles();

        void getWxArticles(int id, boolean isRefresh);

        void getKnowledgeArticles(int cid, boolean isRefresh);

        void getProjectArticles(int cid, boolean isRefresh);

        void getCollect(boolean isRefresh);

        // 收藏
        void collect(int id);

        // 取消收藏
        void unCollect(int id);

        // 取消收藏
        void unCollect(int id, int originId);

        void query(int page, String k);
    }

    interface View extends BaseView<Presenter> {

        void setHomeArticles(List<ArticleBean> topArticleBeans, ArticleBeans articleBeans);

        void setArticles(ArticleBeans articleBeans, boolean isRefresh);

        void collectSuccess();

        void unCollectSuccess();
    }
}