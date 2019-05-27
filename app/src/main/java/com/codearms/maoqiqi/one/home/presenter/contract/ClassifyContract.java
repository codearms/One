package com.codearms.maoqiqi.one.home.presenter.contract;

import com.codearms.maoqiqi.one.home.BasePresenter;
import com.codearms.maoqiqi.one.home.BaseView;
import com.codearms.maoqiqi.one.home.data.bean.ChildClassifyBean;

import java.util.List;

public interface ClassifyContract {

    interface Presenter extends BasePresenter {

        void getProject();
    }

    interface View extends BaseView<Presenter> {

        void setProject(List<ChildClassifyBean> childClassifyBeans);
    }
}