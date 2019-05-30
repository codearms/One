package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlowLayoutPresenter extends RxPresenterImpl<FlowLayoutContract.View> implements FlowLayoutContract.Presenter {

    private OneRepository repository;

    public FlowLayoutPresenter(FlowLayoutContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getKnowledge() {
        addSubscribe(repository.getKnowledge()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<List<ParentClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ParentClassifyBean>> commonBean) {
                        if (!view.isActive()) return;

                        view.setKnowledge(commonBean.getData());
                    }
                }));
    }
}