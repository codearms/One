package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

public class FlowLayoutPresenter extends RxPresenterImpl<FlowLayoutContract.View> implements FlowLayoutContract.Presenter {

    private OneRepository repository;

    public FlowLayoutPresenter(FlowLayoutContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getKnowledge() {
        addSubscribe(repository.getKnowledge().subscribeWith(
                new BaseObserver<List<ParentClassifyBean>>(view, R.string.failed_to_knowledge) {
                    @Override
                    public void onNext(List<ParentClassifyBean> parentClassifyBeans) {
                        if (isActive()) view.setKnowledge(parentClassifyBeans);
                    }
                }));
    }
}