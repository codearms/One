package com.codearms.maoqiqi.one.presenter;

import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.presenter.contract.ArticlesContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ArticlesPresenter implements ArticlesContract.Presenter {

    private ArticlesContract.View articlesView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ArticlesPresenter(ArticlesContract.View articlesView) {
        this.articlesView = articlesView;
        articlesView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getHomeArticles();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getHomeArticles() {
        Observable<CommonBean<List<ArticleBean>>> topArticleObservable = RetrofitManager.getInstance().getServerApi().getTopArticles();
        Observable<CommonBean<ArticleBeans>> articleObservable = RetrofitManager.getInstance().getServerApi().getArticles(0);
        compositeDisposable.add(Observable.zip(topArticleObservable, articleObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!articlesView.isActive()) return;

                        articlesView.setHomeArticles(data.getTopArticleBeans().getData(), data.getArticleBeans().getData());
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
    public void getWxNumberData(int id, int page) {
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().getWxNumberData(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!articlesView.isActive()) return;

                        articlesView.setWxNumberData(commonBean.getData());
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
        private CommonBean<List<ArticleBean>> topArticleBeans;
        private CommonBean<ArticleBeans> articleBeans;

        Data(CommonBean<List<ArticleBean>> topArticleBeans, CommonBean<ArticleBeans> articleBeans) {
            this.topArticleBeans = topArticleBeans;
            this.articleBeans = articleBeans;
        }

        CommonBean<List<ArticleBean>> getTopArticleBeans() {
            return topArticleBeans;
        }

        CommonBean<ArticleBeans> getArticleBeans() {
            return articleBeans;
        }
    }
}