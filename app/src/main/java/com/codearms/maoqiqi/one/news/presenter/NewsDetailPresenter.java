package com.codearms.maoqiqi.one.news.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsDetailContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 18:30
 */
public class NewsDetailPresenter extends RxPresenterImpl<NewsDetailContract.View> implements NewsDetailContract.Presenter {

    private OneRepository repository;

    public NewsDetailPresenter(NewsDetailContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getNewsDetail(int id) {
        addSubscribe(repository.getNewsDetail(id)
                .subscribeWith(new BaseObserver<NewsDetailBean>(view) {
                    @Override
                    public void onNext(NewsDetailBean newsDetailBean) {
                        super.onNext(newsDetailBean);
                        view.setNewsDetail(newsDetailBean);
                    }
                }));
    }
}