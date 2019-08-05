package com.codearms.maoqiqi.one.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.PictureOperationFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.view.GestureView;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PictureActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.PictureOperationFragment";

    private AppBarLayout appBarLayout;
    private View view;

    private boolean showing;
    private String url;

    private PictureOperationFragment fragment;

    public static void start(@NonNull Context context, String url, Bundle options) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, options);
    }

    public static void start(@NonNull Context context, ArrayList<String> urls, int position, Bundle options) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("urls", urls);
        bundle.putInt("position", position);
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_picture);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        url = bundle.getString("url");
        ArrayList<String> urls = bundle.getStringArrayList("urls");
        int position = bundle.getInt("position");

        appBarLayout = findViewById(R.id.app_bar_layout);
        GestureView gestureView = findViewById(R.id.gesture_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView ivImage = findViewById(R.id.iv_image);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TextView tvInfo = findViewById(R.id.tv_info);
        view = findViewById(R.id.view);

        toolbar.setTitle("");
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_more));
        setSupportActionBar(toolbar);

        gestureView.setOnSwipeListener(new GestureView.OnSwipeListener() {
            @Override
            public void downSwipe() {
                tvInfo.setVisibility(View.GONE);
                view.setAlpha(0);
                finish();
                overridePendingTransition(0, R.anim.picture_exit_anim);
            }

            @Override
            public void overSwipe() {
                tvInfo.setVisibility(View.VISIBLE);
                view.setAlpha(1);
            }

            @Override
            public void onSwiping(float y) {
                tvInfo.setVisibility(View.GONE);

                float alpha = 1 - y / 500;
                if (alpha < 0.3) {
                    alpha = 0.3f;
                } else if (alpha > 1) {
                    alpha = 1;
                }
                view.setAlpha(alpha);
            }
        });

        ivImage.setOnClickListener(this::onClick);
        ivImage.setOnLongClickListener(this::onLongClick);

        fragment = (PictureOperationFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) fragment = new PictureOperationFragment();

        if (urls == null) {
            ivImage.setVisibility(VISIBLE);
            viewPager.setVisibility(GONE);
            tvInfo.setVisibility(GONE);
            Glide.with(this).asBitmap().load(url).placeholder(R.drawable.ic_belle_placeholder).into(ivImage);
        } else {
            ivImage.setVisibility(GONE);
            viewPager.setVisibility(VISIBLE);
            tvInfo.setVisibility(VISIBLE);
            tvInfo.setText(String.format("%s / %s", position + 1, urls.size()));
            viewPager.setAdapter(new SectionsPagerAdapter(urls));
            viewPager.setOffscreenPageLimit(1);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    tvInfo.setText(String.format("%s / %s", i + 1, urls.size()));
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    public void onClick(View view) {
        showing = !showing;
        appBarLayout.setVisibility(showing ? VISIBLE : GONE);
    }

    public boolean onLongClick(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            Object tag = imageView.getTag(R.id.tag);
            if (tag == null && url == null) return false;
            String u = url;
            if (tag != null) u = tag.toString();

            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            fragment.setUrl(u);
            fragment.setBitmap(bitmap);
            fragment.show(getSupportFragmentManager(), TAG);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_share) {
            ToastUtils.show("share");
            return true;
        }
        return false;
    }

    public final class SectionsPagerAdapter extends PagerAdapter {

        private ArrayList<String> urls;

        SectionsPagerAdapter(ArrayList<String> urls) {
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = (ImageView) LayoutInflater.from(PictureActivity.this).inflate(R.layout.item_picture, null);
            Glide.with(imageView).load(urls.get(position)).placeholder(R.drawable.ic_belle_placeholder).into(imageView);
            imageView.setTag(R.id.tag, urls.get(position));
            imageView.setOnClickListener(PictureActivity.this::onClick);
            imageView.setOnLongClickListener(PictureActivity.this::onLongClick);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}