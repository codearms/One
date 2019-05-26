package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.WeChatBean;

import java.util.List;

public interface WeChatContract {

    interface Presenter extends BasePresenter {

        void getWxList();
    }

    interface View extends BaseView<Presenter> {

        void setWxList(List<WeChatBean> weChatBeans);
    }
}