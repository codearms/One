package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class WebViewPresenter extends RxPresenterImpl<WebViewContract.View> implements WebViewContract.Presenter {

    private OneRepository repository;

    public WebViewPresenter(WebViewContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void collect(int id) {
        addSubscribe(repository.collect(id).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_collect_data) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        view.collectSuccess(null);
                    }
                }));
    }

    @Override
    public void collect(String title, String author, String link) {
        addSubscribe(repository.collect(title, author, link).subscribeWith(
                new BaseObserver<ArticleBean>(view, R.string.failed_to_collect) {
                    @Override
                    public void onNext(ArticleBean articleBean) {
                        super.onNext(articleBean);
                        view.collectSuccess(articleBean);
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        addSubscribe(repository.unCollect(id).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_un_collect) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        view.unCollectSuccess();
                    }
                }));
    }

    @Override
    public void unCollect(int id, int originId) {
        addSubscribe(repository.unCollect(id, originId).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_un_collect) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        view.unCollectSuccess();
                    }
                }));
    }

    @Override
    public void collectUrl(String name, String link) {
        addSubscribe(repository.collectUrl(name, link).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_collect_data) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                    }
                }));
    }


    @Override
    public void collectUrl(int id, String name, String link) {
        addSubscribe(repository.collectUrl(id, name, link).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_collect) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                    }
                }));
    }

    @Override
    public void unCollectUrl(int id) {
        addSubscribe(repository.unCollectUrl(id).subscribeWith(
                new BaseObserver<Object>(view, R.string.failed_to_un_collect) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                    }
                }));
    }
}