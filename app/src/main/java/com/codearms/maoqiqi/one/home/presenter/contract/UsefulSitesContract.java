package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.UsefulSitesBean;

import java.util.List;

public interface UsefulSitesContract {

    interface Presenter extends BasePresenter<View> {

        void getUsefulSites();
    }

    interface View extends BaseView<Presenter> {

        void setUsefulSites(List<UsefulSitesBean> usefulSitesBeans);
    }
}