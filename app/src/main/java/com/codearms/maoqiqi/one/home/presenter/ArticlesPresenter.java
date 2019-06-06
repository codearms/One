package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.Observable;

public class ArticlesPresenter extends RxPresenterImpl<ArticlesContract.View> implements ArticlesContract.Presenter {

    private OneRepository repository;

    public ArticlesPresenter(ArticlesContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getHomeArticles() {
        Observable<List<ArticleBean>> topArticleObservable = repository.getTopArticles();
        Observable<ArticleBeans> articleObservable = repository.getArticles(0);
        addSubscribe(Observable.zip(topArticleObservable, articleObservable, Data::new)
                .subscribeWith(new BaseObserver<Data>(view) {
                    @Override
                    public void onNext(Data data) {
                        if (!view.isActive()) return;
                        view.setHomeArticles(data.getTopArticleBeans(), data.getArticleBeans());
                    }
                }));
    }

    @Override
    public void getWxArticles(int id, int page) {
        addSubscribe(repository.getWxArticles(id, page)
                .subscribeWith(new BaseObserver<ArticleBeans>(view) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (!view.isActive()) return;
                        view.setArticles(articleBeans, true);
                    }
                }));
    }

    @Override
    public void getKnowledgeArticles(int page, int cid) {
        addSubscribe(repository.getKnowledgeArticles(page, cid)
                .subscribeWith(new BaseObserver<ArticleBeans>(view) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (!view.isActive()) return;
                        view.setArticles(articleBeans, true);
                    }
                }));
    }

    @Override
    public void getProjectArticles(int page, int cid) {
        addSubscribe(repository.getProjectArticles(page, cid)
                .subscribeWith(new BaseObserver<ArticleBeans>(view) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (!view.isActive()) return;
                        view.setArticles(articleBeans, true);
                    }
                }));
    }

    @Override
    public void getCollect(int page) {
        addSubscribe(repository.getCollect(page)
                .subscribeWith(new BaseObserver<ArticleBeans>(view) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (!view.isActive()) return;
                        view.setArticles(articleBeans, true);
                    }
                }));
    }

    @Override
    public void collect(int id) {
        addSubscribe(repository.collect(id)
                .subscribeWith(new BaseObserver<Object>(view) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (!view.isActive()) return;
                        view.collectSuccess();
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        addSubscribe(repository.unCollect(id)
                .subscribeWith(new BaseObserver<Object>(view) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (!view.isActive()) return;
                        view.unCollectSuccess();
                    }
                }));
    }

    @Override
    public void unCollect(int id, int originId) {
        addSubscribe(repository.unCollect(id, originId)
                .subscribeWith(new BaseObserver<Object>(view) {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (!view.isActive()) return;
                        view.unCollectSuccess();
                    }
                }));
    }

    @Override
    public void query(int page, String k) {
        addSubscribe(repository.query(page, k).subscribeWith(
                new BaseObserver<ArticleBeans>(view) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (!view.isActive()) return;
                        view.setArticles(articleBeans, true);
                    }
                }
        ));
    }

    private final class Data {
        private List<ArticleBean> topArticleBeans;
        private ArticleBeans articleBeans;

        Data(List<ArticleBean> topArticleBeans, ArticleBeans articleBeans) {
            this.topArticleBeans = topArticleBeans;
            this.articleBeans = articleBeans;
        }

        List<ArticleBean> getTopArticleBeans() {
            return topArticleBeans;
        }

        ArticleBeans getArticleBeans() {
            return articleBeans;
        }
    }
}