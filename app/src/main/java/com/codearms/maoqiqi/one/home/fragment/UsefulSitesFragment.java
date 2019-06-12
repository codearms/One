package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
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

public class UsefulSitesFragment extends BaseFragment<UsefulSitesContract.Presenter> implements UsefulSitesContract.View {

    private ChipGroup chipGroup;

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
        presenter = new UsefulSitesPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_useful_sites, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        chipGroup = rootView.findViewById(R.id.chip_group);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getUsefulSites();
    }

    @Override
    public void setUsefulSites(List<UsefulSitesBean> usefulSitesBeans) {
        Chip chip;
        for (int j = 0; j < usefulSitesBeans.size(); j++) {
            chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
            chip.setText(usefulSitesBeans.get(j).getName());
            chip.setTextColor(ColorUtils.randomDarkColor());
            final UsefulSitesBean usefulSitesBean = usefulSitesBeans.get(j);
            chip.setOnClickListener(v -> WebViewActivity.start(context, 0, usefulSitesBean.getLink()));
            chipGroup.addView(chip);
        }
    }
}