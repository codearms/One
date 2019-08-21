package com.codearms.maoqiqi.one.navigation.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;

import java.util.List;

public interface SearchContract {

    interface Presenter extends BasePresenter<View> {

        void getHotKey();
    }


    interface View extends BaseView<Presenter> {

        void setHotKey(List<HotKeyBean> hotKeyBeanList);

        void setHistory(List<HotKeyBean> historyList);
    }
}