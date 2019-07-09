package com.codearms.maoqiqi.one.gank.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ItemBean;
import com.codearms.maoqiqi.one.gank.presenter.GankListPresenter;
import com.codearms.maoqiqi.one.gank.presenter.contract.GankListContract;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:04
 */
public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    private String category;

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

        adapter = new RecyclerAdapter(R.layout.item_gank_list, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getData(category, Constants.PAGE_INDEX, Constants.PAGE_COUNT);
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
            // 设置间距
            if (helper.getLayoutPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.cardView.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        CardView cardView;
        ImageView ivWelfare;
        LinearLayout layoutInfo;
        TextView tvDes;
        ImageView ivImage;
        TextView tvTag;
        TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}