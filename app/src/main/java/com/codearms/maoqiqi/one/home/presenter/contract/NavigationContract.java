package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;

import java.util.List;

public interface NavigationContract {

    interface Presenter extends BasePresenter<View> {

        void getNavigation();
    }

    interface View extends BaseView<Presenter> {

        void setNavigation(List<NavigationBean> navigationBeans);
    }
}