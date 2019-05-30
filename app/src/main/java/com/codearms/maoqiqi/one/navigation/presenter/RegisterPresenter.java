package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.RegisterContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends RxPresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    private OneRepository repository;

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void register(String userName, String password, String confirmPassword) {
        addSubscribe(repository.register(userName, password, confirmPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<UserBean>>() {
                    @Override
                    public void onNext(CommonBean<UserBean> commonBean) {
                        if (!view.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            view.userInfo(commonBean.getData());
                        } else {
                            view.showErrorMsg(commonBean.getErrorMsg());
                        }
                    }
                }));
    }
}