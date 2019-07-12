package com.codearms.maoqiqi.one.gank.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.gank.presenter.HeaderImagePresenter;
import com.codearms.maoqiqi.one.gank.presenter.contract.HeaderImageContract;
import com.codearms.maoqiqi.one.home.activity.PictureActivity;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:57
 */
public class HeaderImageFragment extends BaseFragment<HeaderImageContract.Presenter> implements HeaderImageContract.View {

    private HeaderImageCallBack callBack;

    private ImageView ivHeader;
    private String url;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment HeaderImageFragment.
     */
    public static HeaderImageFragment newInstance() {
        return new HeaderImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HeaderImagePresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_header_image, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ivHeader = rootView.findViewById(R.id.iv_header);
        ivHeader.setOnClickListener(v -> {
            if (url != null && !url.equals("")) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivHeader, getString(R.string.transition_picture));
                PictureActivity.start(context, null, url, options.toBundle());
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        getHeaderImage();
    }

    public void getHeaderImage() {
        presenter.getHeaderImage(true);
    }

    @Override
    public void setHeaderImage(String url) {
        this.url = url;
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.ic_belle_placeholder).into(
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivHeader.setImageBitmap(resource);
                        if (callBack != null) callBack.onHeaderImage(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        getHeaderImage();
                    }
                });
    }

    public interface HeaderImageCallBack {

        void onHeaderImage(Bitmap resource);
    }

    public void setHeaderImageCallBack(HeaderImageCallBack headerImageCallBack) {
        this.callBack = headerImageCallBack;
    }
}