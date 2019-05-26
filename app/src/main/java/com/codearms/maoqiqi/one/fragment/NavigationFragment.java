package com.codearms.maoqiqi.one.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.activity.WebViewActivity;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.presenter.contract.NavigationContract;

import java.util.List;

public class NavigationFragment extends LazyLoadFragment implements NavigationContract.View {

    private NavigationContract.Presenter presenter;

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewContent;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationFragment.
     */
    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void setPresenter(NavigationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerViewContent = rootView.findViewById(R.id.recycler_view_content);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.subscribe();
    }

    @Override
    public void setNavigation(List<NavigationBean> navigationBeans) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(navigationBeans));

        recyclerViewContent.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewContent.setItemAnimator(new DefaultItemAnimator());
        recyclerViewContent.setNestedScrollingEnabled(false);
        recyclerViewContent.setAdapter(new ContentRecyclerViewAdapter(navigationBeans));
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<NavigationBean> navigationBeans;

        RecyclerViewAdapter(List<NavigationBean> navigationBeans) {
            this.navigationBeans = navigationBeans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_navigation, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (i == 0) {
                viewHolder.view.setBackgroundResource(R.drawable.bg_item_navigation);
            } else {
                viewHolder.view.setBackgroundResource(R.color.color_divider);
            }
            viewHolder.tvName.setText(navigationBeans.get(i).getName());
            viewHolder.tvName.setOnClickListener(v -> {

            });
        }

        @Override
        public int getItemCount() {
            return navigationBeans.size();
        }
    }

    private final class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    private final class ContentRecyclerViewAdapter extends RecyclerView.Adapter<ContentViewHolder> {

        private List<NavigationBean> navigationBeans;
        private Chip chip;

        ContentRecyclerViewAdapter(List<NavigationBean> navigationBeans) {
            this.navigationBeans = navigationBeans;
        }

        @NonNull
        @Override
        public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_navigation_content, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContentViewHolder viewHolder, int i) {
            final NavigationBean navigationBean = navigationBeans.get(i);
            viewHolder.tvName.setText(navigationBean.getName());

            for (int j = 0; j < navigationBean.getList().size(); j++) {
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_item_navigation_content, null);
                chip.setText(navigationBean.getList().get(j).getTitle());
                final String url = navigationBean.getList().get(j).getLink();
                chip.setOnClickListener(v -> WebViewActivity.start(context, url, 0));
                viewHolder.chipGroup.addView(chip);
            }

            if (i == navigationBeans.size() - 1) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.chipGroup.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        @Override
        public int getItemCount() {
            return navigationBeans.size();
        }
    }

    private final class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ChipGroup chipGroup;

        ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            chipGroup = itemView.findViewById(R.id.chip_group);
        }
    }
}