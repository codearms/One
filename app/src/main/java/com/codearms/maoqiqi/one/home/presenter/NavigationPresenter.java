package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.NavigationContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

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
                new BaseObserver<List<NavigationBean>>(view, R.string.failed_to_navigation) {
                    @Override
                    public void onNext(List<NavigationBean> navigationBeans) {
                        if (isActive()) view.setNavigation(navigationBeans);
                    }
                }));
    }
}