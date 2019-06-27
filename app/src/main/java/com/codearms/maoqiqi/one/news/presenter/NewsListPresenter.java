package com.codearms.maoqiqi.one.news.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsListContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 14:39
 */
public class NewsListPresenter extends RxPresenterImpl<NewsListContract.View> implements NewsListContract.Presenter {

    private OneRepository repository;

    public NewsListPresenter(NewsListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getLatestNews() {
        addSubscribe(repository.getLatestNews()
                .subscribeWith(new BaseObserver<NewsBean>(view) {
                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (!view.isActive()) return;
                        view.setLatestNews(newsBean);
                    }
                }));
    }
}