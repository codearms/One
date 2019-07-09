package com.codearms.maoqiqi.one.gank.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:04
 */
public class GankListFragment extends BaseFragment {

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment GankListFragment.
     */
    public static GankListFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        GankListFragment fragment = new GankListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_gank_list, container, false);
    }

}