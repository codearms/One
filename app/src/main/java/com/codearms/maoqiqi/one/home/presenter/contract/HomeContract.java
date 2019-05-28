package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void getData();
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);

        void setBanner(List<BannerBean> bannerBeanList);
    }
}