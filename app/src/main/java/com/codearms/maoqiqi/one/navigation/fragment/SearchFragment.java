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
import android.widget.ImageView;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;
import com.codearms.maoqiqi.one.navigation.presenter.SearchPresenter;
import com.codearms.maoqiqi.one.navigation.presenter.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View {

    private ChipGroup chipGroup;

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
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item" + i);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getHotKey();
    }

    @Override
    public void setHotKey(List<HotKeyBean> hotKeyBeanList) {
        chipGroup.removeAllViews();
        Chip chip;
        for (int i = 0; i < hotKeyBeanList.size(); i++) {
            String name = hotKeyBeanList.get(i).getName();
            chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
            chip.setText(name);
            chip.setOnClickListener(v -> {
                if (listener != null) listener.onSearch(name);
            });
            chipGroup.addView(chip);
        }
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<String> list;

        RecyclerViewAdapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.tvName.setText(list.get(i));
            viewHolder.view.setOnClickListener(v -> {
                if (listener != null) listener.onSearch("android");
            });
            viewHolder.ivDelete.setOnClickListener(v -> {
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private final class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
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

        void onSearch(String k);
    }

    public void setSearchListener(SearchListener searchListener) {
        this.listener = searchListener;
    }
}