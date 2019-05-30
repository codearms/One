package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticlesPresenter extends RxPresenterImpl<ArticlesContract.View> implements ArticlesContract.Presenter {

    private OneRepository repository;

    public ArticlesPresenter(ArticlesContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getHomeArticles() {
        Observable<CommonBean<List<ArticleBean>>> topArticleObservable = repository.getTopArticles();
        Observable<CommonBean<ArticleBeans>> articleObservable = repository.getArticles(0);
        addSubscribe(Observable.zip(topArticleObservable, articleObservable, Data::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Data>() {
                    @Override
                    public void onNext(Data data) {
                        if (!view.isActive()) return;

                        view.setHomeArticles(data.getTopArticleBeans().getData(), data.getArticleBeans().getData());
                    }
                }));
    }

    @Override
    public void getWxArticles(int id, int page) {
        addSubscribe(repository.getWxArticles(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!view.isActive()) return;

                        view.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getKnowledgeArticles(int page, int cid) {
        addSubscribe(repository.getKnowledgeArticles(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!view.isActive()) return;

                        view.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getProjectArticles(int page, int cid) {
        addSubscribe(repository.getProjectArticles(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!view.isActive()) return;

                        view.setArticles(commonBean.getData());
                    }
                }));
    }

    @Override
    public void getCollect(int page) {
        addSubscribe(repository.getCollect(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<ArticleBeans>>() {
                    @Override
                    public void onNext(CommonBean<ArticleBeans> commonBean) {
                        if (!view.isActive()) return;

                        view.setArticles(commonBean.getData());
                    }
                }));
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