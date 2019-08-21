package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

import io.reactivex.Observable;

public class ArticlesPresenter extends RxPresenterImpl<ArticlesContract.View> implements ArticlesContract.Presenter {

    private OneRepository repository;
    private int pageIndex;

    public ArticlesPresenter(ArticlesContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getHomeArticles() {
        Observable<List<ArticleBean>> topArticleObservable = repository.getTopArticles();
        Observable<ArticleBeans> articleObservable = repository.getArticles(0);
        addSubscribe(Observable.zip(topArticleObservable, articleObservable, Data::new)
                .subscribeWith(new BaseObserver<Data>(view, R.string.failed_to_articles) {
                    @Override
                    public void onNext(Data d) {
                        if (isActive()) view.setHomeArticles(d.getTopArticles(), d.getArticles());
                    }
                }));
    }

    @Override
    public void getWxArticles(int id, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.getWxArticles(id, pageIndex)
                .subscribeWith(new BaseObserver<ArticleBeans>(view, R.string.failed_to_wx_articles) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (isActive()) view.setArticles(articleBeans, isRefresh);
                    }
                }));
    }

    @Override
    public void getKnowledgeArticles(int cid, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.getKnowledgeArticles(pageIndex, cid)
                .subscribeWith(new BaseObserver<ArticleBeans>(view, R.string.failed_to_knowledge_articles) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (isActive()) view.setArticles(articleBeans, isRefresh);
                    }
                }));
    }

    @Override
    public void getProjectArticles(int cid, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.getProjectArticles(pageIndex, cid)
                .subscribeWith(new BaseObserver<ArticleBeans>(view, R.string.failed_to_project_articles) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (isActive()) view.setArticles(articleBeans, isRefresh);
                    }
                }));
    }

    @Override
    public void getCollect(boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.getCollect(pageIndex)
                .subscribeWith(new BaseObserver<ArticleBeans>(view, R.string.failed_to_collect_data) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (isActive()) view.setArticles(articleBeans, isRefresh);
                    }
                }));
    }

    @Override
    public void query(String k, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.query(pageIndex, k).subscribeWith(
                new BaseObserver<ArticleBeans>(view, R.string.failed_to_query) {
                    @Override
                    public void onNext(ArticleBeans articleBeans) {
                        if (isActive()) view.setArticles(articleBeans, isRefresh);
                    }
                }
        ));
    }

    @Override
    public void collect(int id) {
        addSubscribe(repository.collect(id)
                .subscribeWith(new BaseObserver<Object>(view, R.string.failed_to_collect) {
                    @Override
                    public void onComplete() {
                        if (isActive()) view.collectSuccess();
                    }
                }));
    }

    @Override
    public void unCollect(int id) {
        addSubscribe(repository.unCollect(id)
                .subscribeWith(new BaseObserver<Object>(view, R.string.failed_to_un_collect) {
                    @Override
                    public void onComplete() {
                        if (isActive()) view.unCollectSuccess();
                    }
                }));
    }

    // 收藏页面取消收藏
    @Override
    public void unCollect(int id, int originId) {
        addSubscribe(repository.unCollect(id, originId)
                .subscribeWith(new BaseObserver<Object>(view, R.string.failed_to_un_collect) {
                    @Override
                    public void onComplete() {
                        if (isActive()) view.unCollectSuccess();
                    }
                }));
    }

    private final class Data {
        private List<ArticleBean> topArticles;
        private ArticleBeans articles;

        Data(List<ArticleBean> topArticles, ArticleBeans articles) {
            this.topArticles = topArticles;
            this.articles = articles;
        }

        List<ArticleBean> getTopArticles() {
            return topArticles;
        }

        ArticleBeans getArticles() {
            return articles;
        }
    }
}