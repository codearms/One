package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View webViewView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WebViewPresenter(WebViewContract.View webViewView) {
        this.webViewView = webViewView;
        webViewView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


    @Override
    public void collect(int id) {
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!webViewView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            webViewView.collectSuccess();
                        } else {
                            webViewView.showErrorMessage(commonBean.getErrorMsg());
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

    @Override
    public void collect(String title, String author, String link) {
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().collect(title, author, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!webViewView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            webViewView.collectSuccess();
                        } else {
                            webViewView.showErrorMessage(commonBean.getErrorMsg());
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
}