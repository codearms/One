package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;

public interface WebViewContract {

    interface Presenter extends BasePresenter<View> {

        // 收藏
        void collect(int id);

        // 收藏
        void collect(String title, String author, String link);

        // 取消收藏
        void unCollect(int id);

        // 取消收藏
        void unCollect(int id, int originId);

        void collectUrl(String name, String link);

        void collectUrl(int id, String name, String link);

        void unCollectUrl(int id);
    }

    interface View extends BaseView<Presenter> {

        void collectSuccess(ArticleBean articleBean);

        void unCollectSuccess();
    }
}