package com.codearms.maoqiqi.one.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.activity.WeChatActivity;
import com.codearms.maoqiqi.one.data.bean.WeChatBean;
import com.codearms.maoqiqi.one.presenter.contract.WeChatContract;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeChatFragment extends LazyLoadFragment implements WeChatContract.View {

    private WeChatContract.Presenter presenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment WeChatFragment.
     */
    public static WeChatFragment newInstance() {
        return new WeChatFragment();
    }

    @Override
    public void setPresenter(WeChatContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_we_chat, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        ((WeChatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ((WeChatActivity) context).onBackPressed());
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.subscribe();
    }

    @Override
    public void setWxList(List<WeChatBean> weChatBeans) {
        List<String> fragmentTitles = new ArrayList<>();
        for (int i = 0; i < weChatBeans.size(); i++) {
            fragmentTitles.add(weChatBeans.get(i).getName());
        }

        viewPager.setAdapter(new MyPagerAdapter(fragmentTitles, weChatBeans, getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
    }

    private final class MyPagerAdapter extends SectionsPagerAdapter {

        private List<WeChatBean> weChatBeans;

        MyPagerAdapter(List<String> fragmentTitles, List<WeChatBean> weChatBeans, FragmentManager fm) {
            super(fragmentTitles, fm);
            this.weChatBeans = weChatBeans;
        }

        @Override
        public Fragment getItem(int i) {
            return ArticlesFragment.newInstance(ArticlesFragment.FROM_WE_CHAT, weChatBeans.get(i).getId());
        }
    }
}