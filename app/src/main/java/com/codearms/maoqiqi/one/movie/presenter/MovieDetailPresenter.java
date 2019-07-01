package com.codearms.maoqiqi.one.movie.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieDetailContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 11:23
 */
public class MovieDetailPresenter extends RxPresenterImpl<MovieDetailContract.View> implements MovieDetailContract.Presenter {

    private OneRepository repository;

    public MovieDetailPresenter(MovieDetailContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getMovieDetail(String id) {
        addSubscribe(repository.getMovieDetail(id)
                .subscribeWith(new BaseObserver<MovieDetailBean>(view) {

                    @Override
                    public void onNext(MovieDetailBean movieDetailBean) {
                        if (!view.isActive()) return;
                        view.setMovieDetail(movieDetailBean);
                    }
                }));
    }
}