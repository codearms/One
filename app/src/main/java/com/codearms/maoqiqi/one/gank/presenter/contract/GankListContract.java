package com.codearms.maoqiqi.one.gank.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.ItemBean;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:36
 */
public interface GankListContract {

    interface Presenter extends BasePresenter<View> {

        void getData(String type, int pageIndex);
    }

    interface View extends BaseView<Presenter> {

        void setData(List<ItemBean> list);
    }
}