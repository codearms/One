package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.home.data.bean.BannerBean;
import com.codearms.maoqiqi.one.home.data.bean.CommonBean;
import com.codearms.maoqiqi.one.home.data.bean.LoginBean;
import com.codearms.maoqiqi.one.home.data.net.RetrofitManager;
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
        Observable<CommonBean<LoginBean>> loginObservable = RetrofitManager.getInstance().getServerApi().login("123", "123");
        Observable<CommonBean<List<BannerBean>>> bannerObservable = RetrofitManager.getInstance().getServerApi().getBanner();
        compositeDisposable.add(Observable.zip(loginObservable, bannerObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!homeView.isActive()) return;

                        // data.getLoginBean();
                        homeView.setBanner(data.getBannerBeans().getData());
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
        private CommonBean<LoginBean> loginBean;
        private CommonBean<List<BannerBean>> bannerBeans;

        Data(CommonBean<LoginBean> loginBean, CommonBean<List<BannerBean>> bannerBeans) {
            this.loginBean = loginBean;
            this.bannerBeans = bannerBeans;
        }

        CommonBean<LoginBean> getLoginBean() {
            return loginBean;
        }

        CommonBean<List<BannerBean>> getBannerBeans() {
            return bannerBeans;
        }
    }
}