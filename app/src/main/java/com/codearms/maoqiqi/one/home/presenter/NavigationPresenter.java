package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.home.presenter.contract.NavigationContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NavigationPresenter implements NavigationContract.Presenter {

    private NavigationContract.View navigationView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public NavigationPresenter(NavigationContract.View navigationView) {
        this.navigationView = navigationView;
        navigationView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getNavigation();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getNavigation() {
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().getNavigation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<List<NavigationBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<NavigationBean>> commonBean) {
                        if (!navigationView.isActive()) return;

                        navigationView.setNavigation(commonBean.getData());
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