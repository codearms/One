package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;

import java.util.List;

public interface ClassifyContract {

    interface Presenter extends BasePresenter<View> {

        void getProject();
    }

    interface View extends BaseView<Presenter> {

        void setProject(List<ChildClassifyBean> childClassifyBeans);
    }
}