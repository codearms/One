package com.codearms.maoqiqi.one.book.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.BookDetailBean;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 11:23
 */
public interface BookDetailContract {

    interface Presenter extends BasePresenter<View> {

        void getBookDetail(String id);
    }

    interface View extends BaseView<Presenter> {

        void setBookDetail(BookDetailBean bookDetailBean);
    }
}