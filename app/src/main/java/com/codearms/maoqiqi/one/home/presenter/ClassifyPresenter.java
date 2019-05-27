package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.home.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.home.data.bean.CommonBean;
import com.codearms.maoqiqi.one.home.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.home.presenter.contract.ClassifyContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ClassifyPresenter implements ClassifyContract.Presenter {

    private ClassifyContract.View classifyView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ClassifyPresenter(ClassifyContract.View classifyView) {
        this.classifyView = classifyView;
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
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().getProject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<List<ChildClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!classifyView.isActive()) return;

                        classifyView.setProject(commonBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}