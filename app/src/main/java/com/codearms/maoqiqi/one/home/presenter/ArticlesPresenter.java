package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ArticlesPresenter implements ArticlesContract.Presenter {

    private ArticlesContract.View articlesView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ArticlesPresenter(ArticlesContract.View articlesView) {
        this.articlesView = articlesView;
        this.repository = OneRepository.getInstance();
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
        Observable<CommonBean<List<ArticleBean>>> topArticleObservable = repository.getTopArticles();
        Observable<CommonBean<ArticleBeans>> articleObservable = repository.getArticles(0);
        compositeDisposable.add(Observable.zip(topArticleObservable, articleObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!articlesView.isActive()) return;

                        articlesView.setHomeArticles(data.getTopArticleBeans().getData(), data.getArticleBeans().getData());
                    }
                }));
    }

    @Override
    public void getWxArticles(int id, int page) {
        compositeDisposable.add(repository.getWxArticles(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!articlesView.isActive()) return;

                        articlesView.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getKnowledgeArticles(int page, int cid) {
        compositeDisposable.add(repository.getKnowledgeArticles(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!articlesView.isActive()) return;

                        articlesView.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getProjectArticles(int page, int cid) {
        compositeDisposable.add(repository.getProjectArticles(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!articlesView.isActive()) return;

                        articlesView.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getCollect(int page) {
        compositeDisposable.add(repository.getCollect(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!articlesView.isActive()) return;

                        articlesView.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void collect(int id) {
        compositeDisposable.add(repository.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<String>>() {
                    @Override
                    public void onNext(CommonBean<String> commonBean) {
                        if (!articlesView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            articlesView.collectSuccess();
                        } else {
                            articlesView.showErrorMessage(commonBean.getErrorMsg());
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
                        if (!articlesView.isActive()) return;

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
                        if (!articlesView.isActive()) return;

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