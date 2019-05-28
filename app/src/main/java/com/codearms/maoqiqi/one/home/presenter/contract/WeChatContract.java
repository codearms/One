package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;

import java.util.List;

public interface WeChatContract {

    interface Presenter extends BasePresenter {

        void getWxList();
    }

    interface View extends BaseView<Presenter> {

        void setWxList(List<ChildClassifyBean> childClassifyBeans);
    }
}