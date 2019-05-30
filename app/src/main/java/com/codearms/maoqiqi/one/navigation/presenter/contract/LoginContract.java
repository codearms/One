package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.UserBean;

public interface LoginContract {

    interface Presenter extends BasePresenter<View> {

        void login(String userName, String password);
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);
    }
}