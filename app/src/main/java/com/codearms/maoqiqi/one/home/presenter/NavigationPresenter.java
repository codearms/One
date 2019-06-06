package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.NavigationContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

public class NavigationPresenter extends RxPresenterImpl<NavigationContract.View> implements NavigationContract.Presenter {

    private OneRepository repository;

    public NavigationPresenter(NavigationContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getNavigation() {
        addSubscribe(repository.getNavigation().subscribeWith(
                new BaseObserver<List<NavigationBean>>(view) {
                    @Override
                    public void onNext(List<NavigationBean> navigationBeans) {
                        if (!view.isActive()) return;
                        view.setNavigation(navigationBeans);
                    }
                }));
    }
}