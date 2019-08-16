package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.HomeContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

public class HomePresenter extends RxPresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private OneRepository repository;

    public HomePresenter(HomeContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getBanner() {
        addSubscribe(repository.getBanner().subscribeWith(
                new BaseObserver<List<BannerBean>>(view, R.string.failed_to_banner) {
                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {
                        if (isActive()) view.setBanner(bannerBeans);
                    }
                }));
    }
}