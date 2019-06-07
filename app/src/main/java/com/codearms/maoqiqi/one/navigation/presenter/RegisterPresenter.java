package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.RegisterContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

public class RegisterPresenter extends RxPresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    private OneRepository repository;

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void register(String userName, String password, String confirmPassword) {
        addSubscribe(repository.register(userName, password, confirmPassword).subscribeWith(
                new BaseObserver<UserBean>(view, R.string.register) {
                    @Override
                    public void onNext(UserBean userBean) {
                        if (!view.isActive()) return;
                        view.userInfo(userBean);
                    }
                }));
    }
}