package com.codearms.maoqiqi.one.home.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.MainActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.home.activity.KnowledgeActivity;
import com.codearms.maoqiqi.one.home.activity.NavigationActivity;
import com.codearms.maoqiqi.one.home.activity.ProjectActivity;
import com.codearms.maoqiqi.one.home.activity.UsefulSitesActivity;
import com.codearms.maoqiqi.one.home.activity.WeChatActivity;
import com.codearms.maoqiqi.one.home.presenter.HomePresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.HomeContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.utils.GlideImageLoader;
import com.codearms.maoqiqi.utils.ActivityUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {

    private static final String TAG = "com.codearms.maoqiqi.one.ArticlesFragment";

    private Toolbar toolbar;
    private Banner banner;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new HomePresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        toolbar = rootView.findViewById(R.id.toolbar);
        banner = rootView.findViewById(R.id.banner);

        ArticlesFragment fragment = (ArticlesFragment) getChildFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = ArticlesFragment.newInstance(ArticlesFragment.FROM_HOME, 0);
            getChildFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (context instanceof MainActivity)
            ((MainActivity) context).associateDrawerLayout(toolbar);
        presenter.getData();
//        presenter.getBanner();
    }

    @Override
    public void userInfo(UserBean userBean) {
        App.getInstance().setUserBean(userBean);
        ((MainActivity) context).setUserInfo();
    }

    @Override
    public void setBanner(List<BannerBean> bannerBeanList) {
        List<String> imageUrls = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < bannerBeanList.size(); i++) {
            BannerBean bannerBean = bannerBeanList.get(i);
            imageUrls.add(bannerBean.getImagePath());
            titles.add(bannerBean.getTitle());
        }

        banner.setImages(imageUrls).setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(position -> WebViewActivity.start(context, bannerBeanList.get(position).getUrl()))
                .setDelayTime(3000).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    @SuppressLint("PrivateApi")
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (int i = 2; i < menu.size(); i++) {
                    Drawable drawable = menu.getItem(i).getIcon();
                    if (drawable != null)
                        drawable.setTint(getResources().getColor(R.color.color_home));
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_useful_sites:
                // 常用网站
                ActivityUtils.startActivity(context, UsefulSitesActivity.class);
                return true;
            case R.id.menu_navigation:
                // 导航
                ActivityUtils.startActivity(context, NavigationActivity.class);
                return true;
            case R.id.menu_we_chat:
                ActivityUtils.startActivity(context, WeChatActivity.class);
                // 公众号
                return true;
            case R.id.menu_knowledge:
                ActivityUtils.startActivity(context, KnowledgeActivity.class);
                // 知识体系
                return true;
            case R.id.menu_project:
                // 项目
                ActivityUtils.startActivity(context, ProjectActivity.class);
                break;
        }
        return false;
    }
}