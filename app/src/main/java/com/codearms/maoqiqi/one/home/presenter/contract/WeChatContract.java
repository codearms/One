package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;

import java.util.List;

public interface WeChatContract {

    interface Presenter extends BasePresenter<View> {

        void getWxList();
    }

    interface View extends BaseView<Presenter> {

        void setWxList(List<ChildClassifyBean> childClassifyBeans);
    }
}