package com.codearms.maoqiqi.one.gank.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ItemBean;
import com.codearms.maoqiqi.one.gank.presenter.GankListPresenter;
import com.codearms.maoqiqi.one.gank.presenter.contract.GankListContract;
import com.codearms.maoqiqi.one.utils.GankUtils;
import com.codearms.maoqiqi.one.utils.RecyclerViewScrollListener;
import com.codearms.maoqiqi.one.utils.TimeUtils;
import com.codearms.maoqiqi.one.utils.UrlMatch;
import com.codearms.maoqiqi.utils.ScreenUtils;

import java.util.List;

/**
 * 干货列表数据
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-11 14:00
 */
public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    private String category;
    private int pageIndex = 1;

    private RecyclerAdapter adapter;
    private List<ItemBean> list;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new GankListPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_gank_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;

        category = bundle.getString("category");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager manager;
        if (category.equals("福利")) {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            manager = new LinearLayoutManager(context);
        }

        adapter = new RecyclerAdapter(R.layout.item_gank_list, list);

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener(getContext()));
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getData(category, pageIndex);
    }

    @Override
    public void setData(List<ItemBean> list) {
        this.list = list;
        adapter.replaceData(list);
    }

    final class RecyclerAdapter extends BaseQuickAdapter<ItemBean, ViewHolder> {

        RecyclerAdapter(int layoutResId, @Nullable List<ItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, ItemBean item) {
            if (item.getType().equals("福利")) {
                helper.ivWelfare.setVisibility(View.VISIBLE);
                helper.cardView.setVisibility(View.GONE);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) helper.ivWelfare.getLayoutParams();
                params.width = ScreenUtils.getScreenWidth() / 2;

                setImage(helper.ivWelfare, item.getUrl());
            } else {
                helper.ivWelfare.setVisibility(View.GONE);
                helper.cardView.setVisibility(View.VISIBLE);

                helper.tvTitle.setText(item.getDesc());

                if (item.getImages() != null && item.getImages().size() > 0 && !TextUtils.isEmpty(item.getImages().get(0))) {
                    helper.ivImage.setVisibility(View.VISIBLE);
                    setImage(helper.ivImage, item.getImages().get(0));
                } else {
                    helper.ivImage.setVisibility(View.GONE);
                }

                setTag(helper.tvTag, item.getType(), item.getUrl());

                helper.tvAuthor.setText(GankUtils.getWho(item.getWho()));
                helper.tvTime.setText(TimeUtils.getTranslateTime(item.getPublishedAt()));
            }

            // 设置间距
            if (helper.getLayoutPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.frameLayout.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        private void setImage(ImageView imageView, String imageUrl) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }

        private void setTag(TextView tvTag, String type, String url) {
            String key = UrlMatch.processUrl(url);

            GradientDrawable drawable = (GradientDrawable) tvTag.getBackground();
            drawable.setColor(UrlMatch.getColor(type, key));

            String prefix = category.equals("all") ? type + " · " : "";
            tvTag.setText(String.format("%s%s", prefix, UrlMatch.getContent(type, key)));
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        FrameLayout frameLayout;
        CardView cardView;
        TextView tvTitle;
        ImageView ivImage;
        TextView tvTag;
        TextView tvAuthor;
        TextView tvTime;
        ImageView ivWelfare;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_layout);
            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvTag = itemView.findViewById(R.id.tv_tag);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvTime = itemView.findViewById(R.id.tv_date);
            ivWelfare = itemView.findViewById(R.id.iv_welfare);
        }
    }
}