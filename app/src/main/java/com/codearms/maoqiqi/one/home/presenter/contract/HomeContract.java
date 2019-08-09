package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.BannerBean;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter<View> {

        void getBanner();
    }

    interface View extends BaseView<Presenter> {

        void setBanner(List<BannerBean> bannerBeanList);
    }
}