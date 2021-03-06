package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.LoginContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class LoginPresenter extends RxPresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private OneRepository repository;

    public LoginPresenter(LoginContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void login(String userName, String password) {
        addSubscribe(repository.login(userName, password).subscribeWith(
                new BaseObserver<UserBean>(view) {
                    @Override
                    public void onNext(UserBean userBean) {
                        if (isActive()) view.userInfo(userBean);
                    }
                }));
    }
}