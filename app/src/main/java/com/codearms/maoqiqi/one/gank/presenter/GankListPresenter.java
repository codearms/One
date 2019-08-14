package com.codearms.maoqiqi.one.gank.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.data.bean.DataBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.gank.presenter.contract.GankListContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:35
 */
public class GankListPresenter extends RxPresenterImpl<GankListContract.View> implements GankListContract.Presenter {

    private OneRepository repository;

    public GankListPresenter(GankListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getData(String type, int pageIndex) {
        addSubscribe(repository.getData(type, pageIndex, Constants.PAGE_COUNT)
                .subscribeWith(new BaseObserver<DataBean>(view) {
                    @Override
                    public void onNext(DataBean dataBean) {
                        super.onNext(dataBean);
                        view.setData(dataBean.getResultList());
                    }
                }));
    }
}