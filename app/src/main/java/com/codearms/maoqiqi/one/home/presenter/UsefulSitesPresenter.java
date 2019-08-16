package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UsefulSitesBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.UsefulSitesContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

public class UsefulSitesPresenter extends RxPresenterImpl<UsefulSitesContract.View> implements UsefulSitesContract.Presenter {

    private OneRepository repository;

    public UsefulSitesPresenter(UsefulSitesContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(repository.getUsefulSites()
                .subscribeWith(new BaseObserver<List<UsefulSitesBean>>(view, R.string.failed_to_useful_sites) {
                    @Override
                    public void onNext(List<UsefulSitesBean> usefulSitesBeans) {
                        if (isActive()) view.setUsefulSites(usefulSitesBeans);
                    }
                }));
    }
}