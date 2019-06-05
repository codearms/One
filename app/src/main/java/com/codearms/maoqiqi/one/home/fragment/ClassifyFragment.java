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
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.presenter.ClassifyPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.ClassifyContract;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment<ClassifyContract.Presenter> implements ClassifyContract.View {

    public static final String FROM_WE_CHAT = "FROM_WE_CHAT";
    public static final String FROM_PROJECT = "FROM_PROJECT";
    public static final String FROM_CLASSIFY = "FROM_CLASSIFY";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ParentClassifyBean parentClassifyBean;
    private int position;
    private String from;
    private ArticleBean articleBean;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClassifyFragment.
     */
    public static ClassifyFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ClassifyFragment newInstance(ParentClassifyBean parentClassifyBean, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("from", FROM_CLASSIFY);
        bundle.putParcelable("parentClassifyBean", parentClassifyBean);
        bundle.putInt("position", position);
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ClassifyFragment newInstance(String from, ArticleBean articleBean) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putParcelable("articleBean", articleBean);
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
            parentClassifyBean = bundle.getParcelable("parentClassifyBean");
            position = bundle.getInt("position");
            from = bundle.getString("from");
            articleBean = bundle.getParcelable("articleBean");
        }

        switch (from) {
            case FROM_WE_CHAT:
                toolbar.setTitle(R.string.we_chat);
                break;
            case FROM_PROJECT:
                toolbar.setTitle(R.string.project);
                break;
            case FROM_CLASSIFY:
                toolbar.setTitle(parentClassifyBean.getName());
                break;
            default:
                assert articleBean != null;
                toolbar.setTitle(articleBean.getSuperChapterName());
                break;
        }
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ((AppCompatActivity) context).onBackPressed());
    }

    @Override
    protected void loadData() {
        super.loadData();
        switch (from) {
            case FROM_WE_CHAT:
                presenter.getWxList();
                break;
            case FROM_PROJECT:
                presenter.getProject();
                break;
            case FROM_CLASSIFY:
                setClassifies(parentClassifyBean.getChildClassifyBeanList());
                break;
            default:
                presenter.getKnowledge();
                break;
        }
    }

    @Override
    public void setClassifies(List<ChildClassifyBean> childClassifyBeans) {
        List<String> fragmentTitles = new ArrayList<>();
        for (int i = 0; i < childClassifyBeans.size(); i++) {
            fragmentTitles.add(childClassifyBeans.get(i).getName());
        }

        viewPager.setAdapter(new MyPagerAdapter(fragmentTitles, childClassifyBeans, getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(position);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setKnowledge(List<ParentClassifyBean> parentClassifyBeans) {
        ParentClassifyBean parentClassifyBean = null;
        for (int i = 0; i < parentClassifyBeans.size(); i++) {
            if (parentClassifyBeans.get(i).getChildClassifyBeanList().get(0).getId() == articleBean.getSuperChapterId()) {
                parentClassifyBean = parentClassifyBeans.get(i);
                break;
            }
        }
        if (parentClassifyBean == null) return;

        for (int i = 0; i < parentClassifyBean.getChildClassifyBeanList().size(); i++) {
            if (parentClassifyBean.getChildClassifyBeanList().get(i).getId() == articleBean.getChapterId()) {
                position = i;
                break;
            }
        }
        setClassifies(parentClassifyBean.getChildClassifyBeanList());
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