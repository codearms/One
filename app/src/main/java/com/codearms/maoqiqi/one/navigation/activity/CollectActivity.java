package com.codearms.maoqiqi.one.navigation.activity;

import android.support.v4.app.Fragment;

import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.ArticlesFragment;

/**
 * 收藏列表
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 11:30
 */
public class CollectActivity extends FragmentActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.ArticlesFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return ArticlesFragment.newInstance(ArticlesFragment.FROM_COLLECT, 0, false);
    }
}