package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.HomeContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.Observable;

public class HomePresenter extends RxPresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private OneRepository repository;

    public HomePresenter(HomeContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getBanner() {
        addSubscribe(repository.getBanner().subscribeWith(
                new BaseObserver<List<BannerBean>>(view) {
                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {
                        if (!view.isActive()) return;
                        view.setBanner(bannerBeans);
                    }
                }));
    }

    @Override
    public void getData() {
        Observable<UserBean> loginObservable = repository.login("maoqiqi", "123456");
        Observable<List<BannerBean>> bannerObservable = repository.getBanner();
        addSubscribe(Observable.zip(loginObservable, bannerObservable, Data::new).subscribeWith(
                new BaseObserver<Data>(view) {
                    @Override
                    public void onNext(Data data) {
                        if (!view.isActive()) return;
                        view.userInfo(data.getUserData());
                        view.setBanner(data.getBannerData());
                    }
                }));
    }

    private final class Data {
        private UserBean userData;
        private List<BannerBean> bannerData;

        Data(UserBean userData, List<BannerBean> bannerData) {
            this.userData = userData;
            this.bannerData = bannerData;
        }

        UserBean getUserData() {
            return userData;
        }

        List<BannerBean> getBannerData() {
            return bannerData;
        }
    }
}