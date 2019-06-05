package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;

import java.util.List;

public interface ClassifyContract {

    interface Presenter extends BasePresenter<View> {

        void getWxList();

        void getProject();

        void getKnowledge();
    }

    interface View extends BaseView<Presenter> {

        void setClassifies(List<ChildClassifyBean> childClassifyBeans);

        void setKnowledge(List<ParentClassifyBean> parentClassifyBeans);
    }
}