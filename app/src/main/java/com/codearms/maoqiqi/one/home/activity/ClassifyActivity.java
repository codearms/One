package com.codearms.maoqiqi.one.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.fragment.ClassifyFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

public class ClassifyActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.ClassifyFragment";

    // 从知识体系跳转过来
    public static void start(@NonNull Context context, @NonNull ParentClassifyBean parentClassifyBean, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("parentClassifyBean", parentClassifyBean);
        bundle.putInt("position", position);
        Intent intent = new Intent(context, ClassifyActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    // 从文章列表跳转
    public static void start(@NonNull Context context, @NonNull String from, @NonNull ArticleBean articleBean) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putParcelable("articleBean", articleBean);
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
            int position = bundle.getInt("position");

            String from = bundle.getString("from");
            ArticleBean articleBean = bundle.getParcelable("articleBean");

            ClassifyFragment fragment = (ClassifyFragment) getSupportFragmentManager().findFragmentByTag(TAG);
            if (fragment == null) {
                if (parentClassifyBean != null) {
                    fragment = ClassifyFragment.newInstance(parentClassifyBean, position);
                } else {
                    fragment = ClassifyFragment.newInstance(from, articleBean);
                }
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
            ToastUtils.show(this, "search");
            return true;
        }
        return false;
    }
}