package com.codearms.maoqiqi.one.navigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;
import com.codearms.maoqiqi.one.navigation.presenter.SearchPresenter;
import com.codearms.maoqiqi.one.navigation.presenter.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * <p>
 * 关于Fragment中软键盘遮挡内容问题?
 * 给相关Activity设置android:windowSoftInputMode="adjustResize",然后在根视图设置android:fitsSystemWindows="true"
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 15:15
 */
public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View {

    private ChipGroup chipGroup;
    private View line;
    private Button btnClean;
    private RecyclerViewAdapter adapter;
    private List<HotKeyBean> hotKeyBeanList = new ArrayList<>();
    private List<HotKeyBean> historyList = new ArrayList<>();

    private SearchListener listener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new SearchPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        chipGroup = rootView.findViewById(R.id.chip_group);
        line = rootView.findViewById(R.id.line);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        btnClean = rootView.findViewById(R.id.btn_clean);
        btnClean.setOnClickListener(v -> {
            historyList.clear();
            adapter.replaceData(historyList);
            line.setVisibility(View.GONE);
            btnClean.setVisibility(View.GONE);
        });

        adapter = new RecyclerViewAdapter(R.layout.item_history, historyList);
        adapter.setOnItemChildClickListener((adapter, view, position) -> itemChildClick(view, position));

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            setHotKey(hotKeyBeanList);
            if (historyList.size() > 0) {
                line.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.VISIBLE);
            }
        }
    }

    private void itemChildClick(View view, int position) {
        switch (view.getId()) {
            case R.id.view:
                if (listener != null) {
                    listener.onSearch(historyList.get(position).getName(), true);
                }
                break;
            case R.id.iv_delete:
                historyList.remove(position);
                adapter.replaceData(historyList);
                if (adapter.getData().size() == 0) {
                    line.setVisibility(View.GONE);
                    btnClean.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getHotKey();
    }

    @Override
    public void setHotKey(List<HotKeyBean> hotKeyBeanList) {
        loadDataCompleted();
        if (this.hotKeyBeanList != hotKeyBeanList)
            this.hotKeyBeanList.addAll(hotKeyBeanList);

        chipGroup.removeAllViews();
        Chip chip;
        for (int i = 0; i < hotKeyBeanList.size(); i++) {
            String name = hotKeyBeanList.get(i).getName();
            chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
            chip.setText(name);
            chip.setOnClickListener(v -> {
                if (listener != null) listener.onSearch(name, true);
            });
            chipGroup.addView(chip);
        }
    }

    @Override
    public void setHistory(List<HotKeyBean> historyList) {
        this.historyList.addAll(historyList);

        if (historyList.size() > 0) {
            adapter.replaceData(historyList);
            line.setVisibility(View.VISIBLE);
            btnClean.setVisibility(View.VISIBLE);
        }
    }

    static final class RecyclerViewAdapter extends BaseQuickAdapter<HotKeyBean, SearchFragment.ViewHolder> {

        RecyclerViewAdapter(int layoutResId, @Nullable List<HotKeyBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, HotKeyBean item) {
            helper.tvName.setText(item.getName());
            helper.addOnClickListener(R.id.view);
            helper.addOnClickListener(R.id.iv_delete);
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        View view;
        TextView tvName;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            tvName = itemView.findViewById(R.id.tv_name);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface SearchListener {

        void onSearch(String k, boolean setText);
    }

    public void setSearchListener(SearchListener searchListener) {
        this.listener = searchListener;
    }
}