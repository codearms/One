package com.codearms.maoqiqi.one.book.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 10:41
 */
public class BookDetailFragment extends BaseFragment {

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookDetailFragment.
     */
    public static BookDetailFragment newInstance(String id, String title, String imageUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        bundle.putString("imageUrl", imageUrl);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }
}