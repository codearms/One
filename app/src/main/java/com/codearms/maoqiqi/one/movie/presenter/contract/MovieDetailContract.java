package com.codearms.maoqiqi.one.movie.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 11:23
 */
public interface MovieDetailContract {

    interface Presenter extends BasePresenter<View> {

        void getMovieDetail(String id);
    }

    interface View extends BaseView<Presenter> {

        void setMovieDetail(MovieDetailBean movieDetailBean);
    }
}