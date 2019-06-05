package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ClassifyContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

public class ClassifyPresenter extends RxPresenterImpl<ClassifyContract.View> implements ClassifyContract.Presenter {

    private OneRepository repository;

    public ClassifyPresenter(ClassifyContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getWxList() {
        addSubscribe(repository.getWxList().subscribeWith(
                new BaseObserver<CommonBean<List<ChildClassifyBean>>>(view) {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!view.isActive()) return;

                        view.setClassifies(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getProject() {
        addSubscribe(repository.getProject().subscribeWith(
                new BaseObserver<CommonBean<List<ChildClassifyBean>>>(view) {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!view.isActive()) return;

                        view.setClassifies(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getKnowledge() {
        addSubscribe(repository.getKnowledge().subscribeWith(
                new BaseObserver<CommonBean<List<ParentClassifyBean>>>(view) {
                    @Override
                    public void onNext(CommonBean<List<ParentClassifyBean>> commonBean) {
                        if (!view.isActive()) return;

                        view.setKnowledge(commonBean.getData());
                    }
                }));
    }
}