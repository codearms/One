package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.presenter.ClassifyPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.ClassifyContract;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment<ClassifyContract.Presenter> implements ClassifyContract.View {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String from;
    private ParentClassifyBean parentClassifyBean;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClassifyFragment.
     */
    public static ClassifyFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString("from", ArticlesFragment.FROM_PROJECT);
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ClassifyFragment newInstance(ParentClassifyBean parentClassifyBean) {
        Bundle bundle = new Bundle();
        bundle.putString("from", ArticlesFragment.FROM_CLASSIFY);
        bundle.putParcelable("parentClassifyBean", parentClassifyBean);
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClassifyPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from", ArticlesFragment.FROM_PROJECT);
            parentClassifyBean = bundle.getParcelable("parentClassifyBean");
        }

        switch (from) {
            case ArticlesFragment.FROM_PROJECT:
                toolbar.setTitle(R.string.project);
                break;
            case ArticlesFragment.FROM_CLASSIFY:
                if (parentClassifyBean == null) return;
                toolbar.setTitle(parentClassifyBean.getName());
                break;
        }
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ((AppCompatActivity) context).onBackPressed());
    }

    @Override
    protected void loadData() {
        super.loadData();
        switch (from) {
            case ArticlesFragment.FROM_PROJECT:
                presenter.getProject();
                break;
            case ArticlesFragment.FROM_CLASSIFY:
                setProject(parentClassifyBean.getChildClassifyBeanList());
                break;
        }
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
            return ArticlesFragment.newInstance(from, childClassifyBeans.get(i).getId());
        }
    }
}