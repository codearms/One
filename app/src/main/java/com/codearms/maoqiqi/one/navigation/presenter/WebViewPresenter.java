package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View webViewView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WebViewPresenter(WebViewContract.View webViewView) {
        this.webViewView = webViewView;
        this.repository = OneRepository.getInstance();
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
        compositeDisposable.add(repository.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!webViewView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            webViewView.collectSuccess();
                        } else {
                            webViewView.showErrorMessage(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void collect(String title, String author, String link) {
        compositeDisposable.add(repository.collect(title, author, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!webViewView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            webViewView.collectSuccess();
                        } else {
                            webViewView.showErrorMessage(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        compositeDisposable.add(repository.unCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!webViewView.isActive()) return;

                    }
                }));
    }

    @Override
    public void unCollect(int id, int originId) {
        compositeDisposable.add(repository.unCollect(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!webViewView.isActive()) return;

                    }
                }));
    }

    @Override
    public void collectUrl(String name, String link) {
        compositeDisposable.add(repository.collectUrl(name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!webViewView.isActive()) return;

                    }
                }));
    }


    @Override
    public void collectUrl(int id, String name, String link) {
        compositeDisposable.add(repository.collectUrl(id, name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!webViewView.isActive()) return;

                    }
                }));
    }

    @Override
    public void unCollectUrl(int id) {
        compositeDisposable.add(repository.unCollectUrl(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!webViewView.isActive()) return;

                    }
                }));
    }
}