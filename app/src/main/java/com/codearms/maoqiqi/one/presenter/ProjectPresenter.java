package com.codearms.maoqiqi.one.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.WeChatBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.presenter.contract.ProjectContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter implements ProjectContract.Presenter {

    private ProjectContract.View projectView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ProjectPresenter(ProjectContract.View projectView) {
        this.projectView = projectView;
        projectView.setPresenter(this);
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
                .subscribeWith(new DisposableObserver<CommonBean<List<WeChatBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<WeChatBean>> commonBean) {
                        if (!projectView.isActive()) return;

                        projectView.setProject(commonBean.getData());
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