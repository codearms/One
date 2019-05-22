package com.codearms.maoqiqi.one.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;

public class NewsFragment extends LazyLoadFragment {

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }
}