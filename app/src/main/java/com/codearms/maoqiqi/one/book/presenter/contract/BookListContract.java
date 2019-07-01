package com.codearms.maoqiqi.one.book.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.BookListBean;

public interface BookListContract {

    interface Presenter extends BasePresenter<View> {

        void getBook(String q, String tag, int start, int count);
    }

    interface View extends BaseView<Presenter> {

        void setBook(BookListBean bookListBean);
    }
}