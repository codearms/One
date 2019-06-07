package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.LoginContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

public class LoginPresenter extends RxPresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private OneRepository repository;

    public LoginPresenter(LoginContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void login(String userName, String password) {
        addSubscribe(repository.login(userName, password).subscribeWith(
                new BaseObserver<UserBean>(view, R.string.failed_to_login) {
                    @Override
                    public void onNext(UserBean userBean) {
                        if (!view.isActive()) return;
                        view.userInfo(userBean);
                    }
                }));
    }
}