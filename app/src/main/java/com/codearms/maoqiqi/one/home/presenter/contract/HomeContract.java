package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;
import com.codearms.maoqiqi.one.home.data.bean.BannerBean;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void getData();
    }

    interface View extends BaseView<Presenter> {

        void setBanner(List<BannerBean> bannerBeanList);
    }
}