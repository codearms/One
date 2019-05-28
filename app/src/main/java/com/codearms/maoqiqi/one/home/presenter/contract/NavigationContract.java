package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;

import java.util.List;

public interface NavigationContract {

    interface Presenter extends BasePresenter {

        void getNavigation();
    }

    interface View extends BaseView<Presenter> {

        void setNavigation(List<NavigationBean> navigationBeans);
    }
}