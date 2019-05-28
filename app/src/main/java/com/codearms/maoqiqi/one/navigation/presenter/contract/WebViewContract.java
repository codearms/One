package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;

public interface WebViewContract {

    interface Presenter extends BasePresenter {

        void collect(int id);

        void collect(String title, String author, String link);
    }

    interface View extends BaseView<Presenter> {

        void collectSuccess();

        void showErrorMessage(String message);
    }
}