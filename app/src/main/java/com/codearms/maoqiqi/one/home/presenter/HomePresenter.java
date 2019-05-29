package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.home.presenter.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
        homeView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getData();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getData() {
        Observable<CommonBean<UserBean>> loginObservable = RetrofitManager.getInstance().getServerApi().login("maoqiqi", "123456");
        Observable<CommonBean<List<BannerBean>>> bannerObservable = RetrofitManager.getInstance().getServerApi().getBanner();
        compositeDisposable.add(Observable.zip(loginObservable, bannerObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!homeView.isActive()) return;

                        if (data.getUserData().getErrorCode() == 0) {
                            homeView.userInfo(data.getUserData().getData());
                        }
                        if (data.getBannerData().getErrorCode() == 0) {
                            homeView.setBanner(data.getBannerData().getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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