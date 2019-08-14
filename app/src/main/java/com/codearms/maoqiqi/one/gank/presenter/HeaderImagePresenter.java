package com.codearms.maoqiqi.one.gank.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.DataBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.gank.presenter.contract.HeaderImageContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import io.reactivex.Observable;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:35
 */
public class HeaderImagePresenter extends RxPresenterImpl<HeaderImageContract.View> implements HeaderImageContract.Presenter {

    private OneRepository repository;

    public HeaderImagePresenter(HeaderImageContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getHeaderImage(boolean isRandom) {
        Observable<DataBean> observable;
        if (isRandom) {
            observable = repository.getRandomData("福利", 1);
        } else {
            observable = repository.getData("福利", 1, 1);
        }

        addSubscribe(observable.subscribeWith(new BaseObserver<DataBean>(view) {
            @Override
            public void onNext(DataBean dataBean) {
                super.onNext(dataBean);
                if (dataBean != null && dataBean.getResultList() != null && dataBean.getResultList().size() > 0) {
                    view.setHeaderImage(dataBean.getResultList().get(0).getUrl());
                }
            }
        }));
    }
}