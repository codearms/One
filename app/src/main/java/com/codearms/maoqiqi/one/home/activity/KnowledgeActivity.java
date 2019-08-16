package com.codearms.maoqiqi.one.home.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.FlowLayoutFragment;
import com.codearms.maoqiqi.utils.ToastUtils;

/**
 * 知识体系
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-12 14:15
 */
public class KnowledgeActivity extends FragmentActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.FlowLayoutFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return FlowLayoutFragment.newInstance(FlowLayoutFragment.FROM_KNOWLEDGE);
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