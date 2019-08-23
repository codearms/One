package com.codearms.maoqiqi.one.movie.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieListContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class MovieListPresenter extends RxPresenterImpl<MovieListContract.View> implements MovieListContract.Presenter {

    private OneRepository repository;
    private int pageIndex;

    public MovieListPresenter(MovieListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getInTheatersMovies(String city, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.inTheatersMovies(city, pageIndex * Constants.PAGE_COUNT, Constants.PAGE_COUNT)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {
                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (isActive()) view.setData(movieListBean);
                    }
                }));
    }

    @Override
    public void getComingSoonMovies(boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.comingSoonMovies(pageIndex * Constants.PAGE_COUNT, Constants.PAGE_COUNT)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {
                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (isActive()) view.setData(movieListBean);
                    }
                }));
    }

    @Override
    public void searchMovie(String q, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.searchMovie(q, pageIndex * Constants.PAGE_COUNT, Constants.PAGE_COUNT)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {
                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (isActive()) view.setData(movieListBean);
                    }
                }));
    }
}