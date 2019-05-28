package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;
import com.codearms.maoqiqi.one.home.data.bean.UserBean;

public interface LoginContract {

    interface Presenter extends BasePresenter {

        void login(String userName, String password);
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);

        void showErrorMessage(String message);
    }
}