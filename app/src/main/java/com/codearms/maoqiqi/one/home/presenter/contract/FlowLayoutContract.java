package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;

import java.util.List;

public interface FlowLayoutContract {

    interface Presenter extends BasePresenter<View> {

        void getKnowledge();
    }

    interface View extends BaseView<Presenter> {

        void setKnowledge(List<ParentClassifyBean> parentClassifyBeans);
    }
}