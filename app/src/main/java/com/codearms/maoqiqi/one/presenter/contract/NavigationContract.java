package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;

import java.util.List;

public interface NavigationContract {

    interface Presenter extends BasePresenter {

        void getNavigation();
    }

    interface View extends BaseView<Presenter> {

        void setNavigation(List<NavigationBean> navigationBeans);
    }
}