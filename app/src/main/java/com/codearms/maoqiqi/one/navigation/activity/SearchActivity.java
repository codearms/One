package com.codearms.maoqiqi.one.navigation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.ArticlesFragment;
import com.codearms.maoqiqi.one.navigation.fragment.SearchFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.KeyboardUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

/**
 * 搜索
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 15:15
 */
public class SearchActivity extends BaseActivity implements SearchFragment.SearchListener {

    private static final String TAG_SEARCH = "com.codearms.maoqiqi.one.SearchFragment";
    private static final String TAG_ARTICLES = "com.codearms.maoqiqi.one.ArticlesFragment";

    private EditText etSearch;

    private SearchFragment searchFragment;

    public static void start(@NonNull Context context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        etSearch = findViewById(R.id.et_search);
        ImageView ivSearch = findViewById(R.id.iv_search);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        int position = bundle.getInt("position", Constants.DEFAULT_POSITION);

        StatusBarUtils.setStatusColor(this, getResources().getColor(Constants.BG_RES_IDS[position]));
        toolbar.setBackgroundResource(Constants.BG_RES_IDS[position]);
        toolbar.setPopupTheme(Constants.THEME_RES_IDS[position]);
        setSupportActionBar(toolbar);
        ivSearch.setOnClickListener(v -> onSearch(etSearch.getText().toString(), false));

        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setSearchListener(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, searchFragment, TAG_SEARCH).commit();
        }
    }

    @Override
    public void onSearch(String k, boolean setText) {
        if (k == null || k.equals("")) {
            ToastUtils.show(getString(R.string.prompt_search_empty));
            return;
        }
        // 赋值
        if (setText) {
            etSearch.setText(k);
            etSearch.setSelection(etSearch.getText().length());
        }

        // 搜索内容时,如果软键盘显示,则隐藏
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (searchFragment != null) ft.hide(searchFragment);

        ArticlesFragment articlesFragment = (ArticlesFragment) getSupportFragmentManager().findFragmentByTag(TAG_ARTICLES);
        if (articlesFragment == null) {
            articlesFragment = ArticlesFragment.newInstance(ArticlesFragment.FROM_SEARCH, k);
            // TODO: 2019-08-21 会有bug存在,java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
            ft.add(R.id.fl_content, articlesFragment, TAG_ARTICLES).addToBackStack(null).commit();
        } else {
            articlesFragment.setSearchData(k);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (KeyboardUtils.isSoftInputVisible(this)) {
                KeyboardUtils.hideSoftInput(this);
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}