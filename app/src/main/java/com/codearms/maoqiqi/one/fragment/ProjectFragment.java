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
import com.codearms.maoqiqi.one.activity.ProjectActivity;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.presenter.contract.ProjectContract;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends LazyLoadFragment implements ProjectContract.View {

    ProjectContract.Presenter presenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProjectFragment.
     */
    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void setPresenter(ProjectContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        ((ProjectActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ((ProjectActivity) context).onBackPressed());
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.subscribe();
    }

    @Override
    public void setProject(List<ChildClassifyBean> childClassifyBeans) {
        List<String> fragmentTitles = new ArrayList<>();
        for (int i = 0; i < childClassifyBeans.size(); i++) {
            fragmentTitles.add(childClassifyBeans.get(i).getName());
        }

        viewPager.setAdapter(new MyPagerAdapter(fragmentTitles, childClassifyBeans, getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
    }

    private final class MyPagerAdapter extends SectionsPagerAdapter {

        private List<ChildClassifyBean> childClassifyBeans;

        MyPagerAdapter(List<String> fragmentTitles, List<ChildClassifyBean> childClassifyBeans, FragmentManager fm) {
            super(fragmentTitles, fm);
            this.childClassifyBeans = childClassifyBeans;
        }

        @Override
        public Fragment getItem(int i) {
            return ArticlesFragment.newInstance(ArticlesFragment.FROM_PROJECT, childClassifyBeans.get(i).getId());
        }
    }
}