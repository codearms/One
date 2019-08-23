package com.codearms.maoqiqi.one.news.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsListContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class NewsListPresenter extends RxPresenterImpl<NewsListContract.View> implements NewsListContract.Presenter {

    private OneRepository repository;

    public NewsListPresenter(NewsListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getLatestNews(boolean isRefresh) {
        addSubscribe(repository.getLatestNews()
                .subscribeWith(new BaseObserver<NewsBean>(view) {
                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (isActive()) view.setLatestNews(newsBean, isRefresh);
                    }
                }));
    }
}