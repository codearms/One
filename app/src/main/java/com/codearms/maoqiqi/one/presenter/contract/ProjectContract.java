package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;

import java.util.List;

public interface ProjectContract {

    interface Presenter extends BasePresenter {

        void getProject();
    }

    interface View extends BaseView<Presenter> {

        void setProject(List<ChildClassifyBean> childClassifyBeans);
    }
}