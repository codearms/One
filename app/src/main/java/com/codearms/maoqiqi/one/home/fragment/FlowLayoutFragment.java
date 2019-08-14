package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
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
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.activity.ClassifyActivity;
import com.codearms.maoqiqi.one.home.presenter.FlowLayoutPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.utils.ColorUtils;

import java.util.List;

public class FlowLayoutFragment extends BaseFragment<FlowLayoutContract.Presenter> implements FlowLayoutContract.View {

    public static final String FROM_NAVIGATION = "FROM_NAVIGATION";
    public static final String FROM_KNOWLEDGE = "FROM_KNOWLEDGE";

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    private String from;
    private List<NavigationBean> navigationBeans;

    private boolean isClick;
    // 记录需要滚动的位置
    private int position;
    private boolean needScroll;
    private SmoothScrollListener listener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment FlowLayoutFragment.
     */
    public static FlowLayoutFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        FlowLayoutFragment fragment = new FlowLayoutFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setNavigationBeans(List<NavigationBean> navigationBeans) {
        this.navigationBeans = navigationBeans;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FlowLayoutPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_flow_layout, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        refreshLayout.setColorSchemeResources(R.color.color_home, R.color.color_news,
                R.color.color_book, R.color.color_music, R.color.color_movie);
        refreshLayout.setEnabled(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from", FROM_NAVIGATION);
        }

        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void addOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (needScroll) {
                        scrollRecyclerView();
                    } else {
                        isClick = false;
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isClick && listener != null) {
                    listener.onSmoothScrollToPosition(manager.findFirstVisibleItemPosition());
                }
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        switch (from) {
            case FROM_NAVIGATION:
                recyclerView.setAdapter(new RecyclerViewAdapter(navigationBeans, null));
                addOnScrollListener();
                break;
            case FROM_KNOWLEDGE:
                refreshLayout.setRefreshing(true);
                presenter.getKnowledge();
                break;
        }
    }

    @Override
    public void setKnowledge(List<ParentClassifyBean> parentClassifyBeans) {
        loadDataCompleted();
        refreshLayout.setRefreshing(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(null, parentClassifyBeans));
    }

    // 滚动到顶部
    public void smoothScrollToPosition(int position) {
        this.isClick = true;
        this.position = position;

        int firstPosition = manager.findFirstVisibleItemPosition();
        int lastPosition = manager.findLastVisibleItemPosition();

        recyclerView.stopScroll();
        if (position <= firstPosition) {
            // 第一种可能:跳转位置在第一个可见位置之前
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastPosition) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstPosition;
            if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                int top = recyclerView.getChildAt(movePosition).getTop();
                recyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            recyclerView.smoothScrollToPosition(position);
            needScroll = true;
        }
    }

    // 在第三种可能下继续滚动
    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = position - manager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < recyclerView.getChildCount()) {
            int top = recyclerView.getChildAt(indexDistance).getTop();
            recyclerView.smoothScrollBy(0, top);
        }
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<NavigationBean> navigationBeans;
        private List<ParentClassifyBean> parentClassifyBeans;
        private Chip chip;

        RecyclerViewAdapter(List<NavigationBean> navigationBeans, List<ParentClassifyBean> parentClassifyBeans) {
            this.navigationBeans = navigationBeans;
            this.parentClassifyBeans = parentClassifyBeans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_flow_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            switch (from) {
                case FROM_NAVIGATION:
                    setNavigation(viewHolder, i);
                    break;
                case FROM_KNOWLEDGE:
                    setKnowledge(viewHolder, i);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return from.equals(FROM_NAVIGATION) ? navigationBeans.size() : parentClassifyBeans.size();
        }

        private void setNavigation(@NonNull ViewHolder viewHolder, int i) {
            final NavigationBean bean = navigationBeans.get(i);
            viewHolder.tvName.setText(bean.getName());

            viewHolder.chipGroup.removeAllViews();
            for (int j = 0; j < bean.getArticleBeanList().size(); j++) {
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
                chip.setText(bean.getArticleBeanList().get(j).getTitle());
                chip.setTextColor(ColorUtils.randomDarkColor());
                final ArticleBean articleBean = bean.getArticleBeanList().get(j);
                chip.setOnClickListener(v -> WebViewActivity.start(context, 0, from, articleBean));
                viewHolder.chipGroup.addView(chip);
            }

            if (i == navigationBeans.size() - 1) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.chipGroup.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        private void setKnowledge(@NonNull ViewHolder viewHolder, int i) {
            final ParentClassifyBean bean = parentClassifyBeans.get(i);
            viewHolder.tvName.setText(bean.getName());

            viewHolder.chipGroup.removeAllViews();
            for (int j = 0; j < bean.getChildClassifyBeanList().size(); j++) {
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_chip, null);
                chip.setText(bean.getChildClassifyBeanList().get(j).getName());
                chip.setTextColor(ColorUtils.randomDarkColor());
                chip.setTag(j);
                chip.setOnClickListener(v -> {
                    int index = (int) v.getTag();
                    ClassifyActivity.start(context, bean, index);
                });
                viewHolder.chipGroup.addView(chip);
            }

            if (i == parentClassifyBeans.size() - 1) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.chipGroup.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }
    }

    private final class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ChipGroup chipGroup;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            chipGroup = itemView.findViewById(R.id.chip_group);
        }
    }

    public interface SmoothScrollListener {

        void onSmoothScrollToPosition(int position);
    }

    public void setSmoothScrollListener(SmoothScrollListener smoothScrollListener) {
        this.listener = smoothScrollListener;
    }
}