package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.UsefulSitesBean;
import com.codearms.maoqiqi.one.home.presenter.UsefulSitesPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.UsefulSitesContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.utils.ColorUtils;

import java.util.List;

/**
 * 常用网址
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-09 11:15
 */
public class UsefulSitesFragment extends BaseFragment<UsefulSitesContract.Presenter> implements UsefulSitesContract.View {

    private SwipeRefreshLayout refreshLayout;
    private ChipGroup chipGroup;

    private List<UsefulSitesBean> usefulSitesBeanList;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment UsefulSitesFragment.
     */
    public static UsefulSitesFragment newInstance() {
        return new UsefulSitesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new UsefulSitesPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_useful_sites, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        chipGroup = rootView.findViewById(R.id.chip_group);

        refreshLayout.setColorSchemeResources(R.color.color_home, R.color.color_news,
                R.color.color_book, R.color.color_music, R.color.color_movie);
        refreshLayout.setEnabled(false);

        if (savedInstanceState != null) {
            setUsefulSites(usefulSitesBeanList);
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        refreshLayout.setRefreshing(true);
        presenter.getUsefulSites();
    }

    @Override
    public void setUsefulSites(List<UsefulSitesBean> usefulSitesBeanList) {
        loadDataCompleted();
        refreshLayout.setRefreshing(false);
        this.usefulSitesBeanList = usefulSitesBeanList;

        Chip chip;
        for (int j = 0; j < usefulSitesBeanList.size(); j++) {
            chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
            chip.setChipBackgroundColor(ColorUtils.createColorStateList());
            chip.setText(usefulSitesBeanList.get(j).getName());
            final UsefulSitesBean usefulSitesBean = usefulSitesBeanList.get(j);
            chip.setOnClickListener(v -> WebViewActivity.start(context, 0, usefulSitesBean.getLink()));
            chipGroup.addView(chip);
        }
    }
}