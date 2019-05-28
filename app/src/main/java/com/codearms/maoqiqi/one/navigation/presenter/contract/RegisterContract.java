package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;

public interface RegisterContract {

    interface Presenter extends BasePresenter {

        void register(String userName, String password, String confirmPassword);
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);

        void showErrorMessage(String message);
    }
}