package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;
import com.codearms.maoqiqi.one.data.bean.KnowledgeBean;

import java.util.List;

public interface KnowledgeContract {

    interface Presenter extends BasePresenter {

        void getKnowledge();
    }

    interface View extends BaseView<Presenter> {

        void setKnowledge(List<KnowledgeBean> knowledgeBeans);
    }
}