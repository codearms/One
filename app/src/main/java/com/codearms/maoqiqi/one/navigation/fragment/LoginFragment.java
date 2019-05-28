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

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.utils.ActivityUtils;
import com.codearms.maoqiqi.one.navigation.activity.RegisterActivity;

public class LoginFragment extends LazyLoadFragment implements View.OnClickListener {

    private EditText etEmail;
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
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        etEmail = rootView.findViewById(R.id.et_email);
        etPassword = rootView.findViewById(R.id.et_password);
        btnLogin = rootView.findViewById(R.id.btn_login);
        TextView btnNewUserRegister = rootView.findViewById(R.id.btn_new_user_register);

        MyTextWatcher textWatcher = new MyTextWatcher();
        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(this);
        btnNewUserRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_new_user_register:
                ActivityUtils.startActivity(context, RegisterActivity.class);
                break;
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
            btnLogin.setEnabled(etEmail.getText().length() != 0 && etPassword.getText().length() != 0);
        }
    }
}