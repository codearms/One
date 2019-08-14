package com.codearms.maoqiqi.one.navigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.MainActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.navigation.activity.RegisterActivity;
import com.codearms.maoqiqi.one.navigation.presenter.LoginPresenter;
import com.codearms.maoqiqi.one.navigation.presenter.contract.LoginContract;
import com.codearms.maoqiqi.utils.ActivityUtils;

/**
 * 登录
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 14:10
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    private boolean autoLogin;

    private String userName;
    private String password;

    private LoginCallBack loginCallBack;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(boolean autoLogin) {
        Bundle args = new Bundle();
        args.putBoolean("autoLogin", autoLogin);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        etUserName = rootView.findViewById(R.id.et_user_name);
        etPassword = rootView.findViewById(R.id.et_password);
        btnLogin = rootView.findViewById(R.id.btn_login);
        TextView btnNewUserRegister = rootView.findViewById(R.id.btn_new_user_register);

        MyTextWatcher textWatcher = new MyTextWatcher();
        etUserName.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(this);
        btnNewUserRegister.setOnClickListener(this);

        if (getArguments() != null) {
            autoLogin = getArguments().getBoolean("autoLogin");
            if (autoLogin) {
                presenter.login(App.getInstance().getUserName(), App.getInstance().getPassword());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                showLoading();
                userName = etUserName.getText().toString();
                password = etPassword.getText().toString();
                presenter.login(userName, password);
                break;
            case R.id.btn_new_user_register:
                ActivityUtils.startActivity(context, RegisterActivity.class);
                break;
        }
    }

    @Override
    public void userInfo(UserBean userBean) {
        hideLoading();
        App.getInstance().setIsLogin(true);
        if (autoLogin) {
            if (loginCallBack != null) loginCallBack.loginStatus(true);
        } else {
            App.getInstance().setUserName(userName);
            App.getInstance().setPassword(password);
            MainActivity.start(context);
        }
    }

    @Override
    public void showErrorMsg(int resId) {
        if (autoLogin) {
            if (loginCallBack != null) loginCallBack.loginStatus(false);
        } else {
            super.showErrorMsg(resId);
        }
    }

    private final class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            btnLogin.setEnabled(etUserName.getText().length() != 0 && etPassword.getText().length() != 0);
        }
    }

    public interface LoginCallBack {

        void loginStatus(boolean status);
    }

    public LoginFragment setCallBack(LoginCallBack callBack) {
        this.loginCallBack = callBack;
        return this;
    }
}