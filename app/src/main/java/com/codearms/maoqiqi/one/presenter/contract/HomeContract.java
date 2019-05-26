package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.BannerBean;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void getData();
    }

    interface View extends BaseView<Presenter> {

        void setBanner(List<BannerBean> bannerBeanList);
    }
}