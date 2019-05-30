package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.HomeContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends RxPresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private OneRepository repository;

    public HomePresenter(HomeContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getData() {
        Observable<CommonBean<UserBean>> loginObservable = repository.login("maoqiqi", "123456");
        Observable<CommonBean<List<BannerBean>>> bannerObservable = repository.getBanner();
        addSubscribe(Observable.zip(loginObservable, bannerObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!view.isActive()) return;

                        if (data.getUserData().getErrorCode() == 0) {
                            view.userInfo(data.getUserData().getData());
                        }
                        if (data.getBannerData().getErrorCode() == 0) {
                            view.setBanner(data.getBannerData().getData());
                        }
                    }
                }));
    }

    private final class Data {
        private CommonBean<UserBean> userData;
        private CommonBean<List<BannerBean>> bannerData;

        Data(CommonBean<UserBean> userData, CommonBean<List<BannerBean>> bannerData) {
            this.userData = userData;
            this.bannerData = bannerData;
        }

        CommonBean<UserBean> getUserData() {
            return userData;
        }

        CommonBean<List<BannerBean>> getBannerData() {
            return bannerData;
        }
    }
}