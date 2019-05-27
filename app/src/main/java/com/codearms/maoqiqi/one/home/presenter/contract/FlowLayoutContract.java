package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;
import com.codearms.maoqiqi.one.home.data.bean.ParentClassifyBean;

import java.util.List;

public interface FlowLayoutContract {

    interface Presenter extends BasePresenter {

        void getKnowledge();
    }

    interface View extends BaseView<Presenter> {

        void setKnowledge(List<ParentClassifyBean> parentClassifyBeans);
    }
}