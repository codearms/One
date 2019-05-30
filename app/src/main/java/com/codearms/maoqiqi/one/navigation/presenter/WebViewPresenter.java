package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WebViewPresenter extends RxPresenterImpl<WebViewContract.View> implements WebViewContract.Presenter {

    private OneRepository repository;

    public WebViewPresenter(WebViewContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void collect(int id) {
        addSubscribe(repository.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.collectSuccess();
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void collect(String title, String author, String link) {
        addSubscribe(repository.collect(title, author, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.collectSuccess();
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        addSubscribe(repository.unCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }

    @Override
    public void unCollect(int id, int originId) {
        addSubscribe(repository.unCollect(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }

    @Override
    public void collectUrl(String name, String link) {
        addSubscribe(repository.collectUrl(name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }


    @Override
    public void collectUrl(int id, String name, String link) {
        addSubscribe(repository.collectUrl(id, name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }

    @Override
    public void unCollectUrl(int id) {
        addSubscribe(repository.unCollectUrl(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean>() {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }
}