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
import com.codearms.maoqiqi.one.utils.ActivityUtils;

public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter presenter;

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
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

       /* InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            mAccountEdit.requestFocus();
            inputMethodManager.showSoftInput(mAccountEdit, 0);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                presenter.login(etUserName.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.btn_new_user_register:
                ActivityUtils.startActivity(context, RegisterActivity.class);
                break;
        }
    }

    @Override
    public void userInfo(UserBean userBean) {
        App.getInstance().setUserBean(userBean);
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
            btnLogin.setEnabled(etUserName.getText().length() != 0 && etPassword.getText().length() != 0);
        }
    }
}