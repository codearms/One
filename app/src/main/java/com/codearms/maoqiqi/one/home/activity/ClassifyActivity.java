package com.codearms.maoqiqi.one.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.fragment.ClassifyFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Toasty;

public class ClassifyActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.ClassifyFragment";

    public static void start(@NonNull Context context, @NonNull ParentClassifyBean parentClassifyBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("parentClassifyBean", parentClassifyBean);
        Intent intent = new Intent(context, ClassifyActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_classify);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ParentClassifyBean parentClassifyBean = bundle.getParcelable("parentClassifyBean");

            ClassifyFragment fragment = (ClassifyFragment) getSupportFragmentManager().findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = ClassifyFragment.newInstance(parentClassifyBean);
                getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
            }
        }
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