package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.UserBean;

public interface RegisterContract {

    interface Presenter extends BasePresenter<View> {

        void register(String userName, String password, String confirmPassword);
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);
    }
}