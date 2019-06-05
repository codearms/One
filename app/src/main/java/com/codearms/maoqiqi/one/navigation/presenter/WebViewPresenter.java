package com.codearms.maoqiqi.one.navigation.presenter;

import android.util.Log;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

public class WebViewPresenter extends RxPresenterImpl<WebViewContract.View> implements WebViewContract.Presenter {

    private OneRepository repository;

    public WebViewPresenter(WebViewContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void collect(int id) {
        addSubscribe(repository.collect(id).subscribeWith(
                new BaseObserver<CommonBean<String>>(view) {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.collectSuccess(null);
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void collect(String title, String author, String link) {
        Log.e("info", title + "--" + author + link);
        addSubscribe(repository.collect(title, author, link).subscribeWith(
                new BaseObserver<CommonBean<ArticleBean>>(view) {
                    @Override
                    public void onNext(CommonBean<ArticleBean> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.collectSuccess(commonBean.getData());
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        addSubscribe(repository.unCollect(id).subscribeWith(
                new BaseObserver<CommonBean<String>>(view) {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.unCollectSuccess();
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void unCollect(int id, int originId) {
        addSubscribe(repository.unCollect(id, originId).subscribeWith(
                new BaseObserver<CommonBean<String>>(view) {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.unCollectSuccess();
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void collectUrl(String name, String link) {
        addSubscribe(repository.collectUrl(name, link).subscribeWith(
                new BaseObserver<CommonBean>(view) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }


    @Override
    public void collectUrl(int id, String name, String link) {
        addSubscribe(repository.collectUrl(id, name, link).subscribeWith(
                new BaseObserver<CommonBean>(view) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }

    @Override
    public void unCollectUrl(int id) {
        addSubscribe(repository.unCollectUrl(id).subscribeWith(
                new BaseObserver<CommonBean>(view) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (!view.isActive()) return;

                    }
                }));
    }
}