package com.codearms.maoqiqi.one.home.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.ArticlesFragment;
import com.codearms.maoqiqi.one.home.fragment.ClassifyFragment;
import com.codearms.maoqiqi.utils.ToastUtils;

/**
 * 公众号
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-12 11:15
 */
public class WeChatActivity extends FragmentActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.ClassifyFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_we_chat;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return ClassifyFragment.newInstance(ArticlesFragment.FROM_WE_CHAT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_search) {
            ToastUtils.show("search");
            return true;
        }
        return false;
    }
}