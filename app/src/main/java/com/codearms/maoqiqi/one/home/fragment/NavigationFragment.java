package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.home.presenter.contract.NavigationContract;

import java.util.List;

public class NavigationFragment extends LazyLoadFragment implements NavigationContract.View {

    private static final String TAG = "com.codearms.maoqiqi.one.FlowLayoutFragment";

    private NavigationContract.Presenter presenter;

    private RecyclerView recyclerView;

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

        FlowLayoutFragment fragment = (FlowLayoutFragment) getChildFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = FlowLayoutFragment.newInstance(FlowLayoutFragment.FROM_NAVIGATION);
            fragment.setNavigationBeans(navigationBeans);
            getChildFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
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
}