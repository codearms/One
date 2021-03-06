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

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.MainActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.navigation.presenter.RegisterPresenter;
import com.codearms.maoqiqi.one.navigation.presenter.contract.RegisterContract;

/**
 * 注册
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 15:15
 */
public class RegisterFragment extends BaseFragment<RegisterContract.Presenter> implements RegisterContract.View {

    private EditText etUserName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    private String userName;
    private String password;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        etUserName = rootView.findViewById(R.id.et_user_name);
        etPassword = rootView.findViewById(R.id.et_password);
        etConfirmPassword = rootView.findViewById(R.id.et_confirm_password);
        btnRegister = rootView.findViewById(R.id.btn_register);

        MyTextWatcher textWatcher = new MyTextWatcher();
        etUserName.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);

        btnRegister.setOnClickListener(v -> {
            showLoading();
            userName = etUserName.getText().toString();
            password = etPassword.getText().toString();
            presenter.register(userName, password, etConfirmPassword.getText().toString());
        });
    }

    @Override
    public void userInfo(UserBean userBean) {
        hideLoading();
        App.getInstance().setIsLogin(true);
        App.getInstance().setUserName(userName);
        App.getInstance().setPassword(password);
        MainActivity.start(context);
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
            btnRegister.setEnabled(etUserName.getText().length() != 0
                    && etPassword.getText().length() != 0
                    && etConfirmPassword.getText().length() != 0);
        }
    }
}