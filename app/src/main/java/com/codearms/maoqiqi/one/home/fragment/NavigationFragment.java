package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.home.presenter.NavigationPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.NavigationContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-09 14:15
 */
public class NavigationFragment extends BaseFragment<NavigationContract.Presenter> implements NavigationContract.View, FlowLayoutFragment.SmoothScrollListener {

    private static final String TAG = "com.codearms.maoqiqi.one.FlowLayoutFragment";

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private RecyclerViewAdapter adapter;

    private List<NavigationBean> navigationBeanList = new ArrayList<>();

    private FlowLayoutFragment fragment;
    // 当前选中项
    private int position;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationFragment.
     */
    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new NavigationPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        refreshLayout.setColorSchemeResources(R.color.color_home, R.color.color_news,
                R.color.color_book, R.color.color_music, R.color.color_movie);
        refreshLayout.setEnabled(false);

        manager = new LinearLayoutManager(context);
        adapter = new RecyclerViewAdapter(navigationBeanList);

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        refreshLayout.setRefreshing(true);
        presenter.getNavigation();
    }

    @Override
    public void setNavigation(List<NavigationBean> navigationBeanList) {
        loadDataCompleted();
        refreshLayout.setRefreshing(false);
        this.navigationBeanList = navigationBeanList;

        adapter.replaceData(navigationBeanList);

        fragment = (FlowLayoutFragment) getChildFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = FlowLayoutFragment.newInstance(FlowLayoutFragment.FROM_NAVIGATION);
            fragment.setNavigationBeans(navigationBeanList);
            fragment.setSmoothScrollListener(this);
            getChildFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }

    @Override
    public void onSmoothScrollToPosition(int i) {
        if (position != i) {
            // 更新选中项
            position = i;
            adapter.notifyDataSetChanged();

            // 如果选中项不可见,需要滚动到选中项位置
            int firstPosition = manager.findFirstVisibleItemPosition();
            int lastPosition = manager.findLastVisibleItemPosition();
            if (position < firstPosition || position > lastPosition) {
                recyclerView.smoothScrollToPosition(position);
            }
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
            if (i == position) {
                viewHolder.view.setBackgroundResource(R.drawable.bg_item_navigation);
            } else {
                viewHolder.view.setBackgroundResource(R.color.color_divider);
            }
            viewHolder.tvName.setText(navigationBeans.get(i).getName());
            viewHolder.tvName.setTag(i);
            viewHolder.tvName.setOnClickListener(v -> {
                position = (int) v.getTag();
                if (fragment != null) fragment.smoothScrollToPosition(position);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return navigationBeans.size();
        }

        private void replaceData(List<NavigationBean> navigationBeans) {
            this.navigationBeans = navigationBeans;
            notifyDataSetChanged();
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