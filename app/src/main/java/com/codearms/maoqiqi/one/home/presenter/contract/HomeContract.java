package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter<View> {

        void getBanner();

        void getData();
    }

    interface View extends BaseView<Presenter> {

        void userInfo(UserBean userBean);

        void setBanner(List<BannerBean> bannerBeanList);
    }
}