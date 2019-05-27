package com.codearms.maoqiqi.one.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.FlowLayoutFragment;
import com.codearms.maoqiqi.one.home.presenter.FlowLayoutPresenter;
import com.codearms.maoqiqi.one.home.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.home.utils.Toasty;

public class KnowledgeActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.FlowLayoutFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_knowledge);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        FlowLayoutFragment fragment = (FlowLayoutFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = FlowLayoutFragment.newInstance(FlowLayoutFragment.FROM_KNOWLEDGE);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }

        new FlowLayoutPresenter(fragment);
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
            Toasty.show(this, "search");
            return true;
        }
        return false;
    }
}