package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ClassifyContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ClassifyPresenter implements ClassifyContract.Presenter {

    private ClassifyContract.View classifyView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ClassifyPresenter(ClassifyContract.View classifyView) {
        this.classifyView = classifyView;
        this.repository = OneRepository.getInstance();
        classifyView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getProject();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getProject() {
        compositeDisposable.add(repository.getProject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<List<ChildClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!classifyView.isActive()) return;

                        classifyView.setProject(commonBean.getData());
                    }
                }));
    }
}