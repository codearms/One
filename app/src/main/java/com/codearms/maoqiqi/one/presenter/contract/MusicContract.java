package com.codearms.maoqiqi.one.presenter.contract;

import com.codearms.maoqiqi.one.BasePresenter;
import com.codearms.maoqiqi.one.BaseView;

public interface MusicContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<HomeContract.Presenter> {

    }
}