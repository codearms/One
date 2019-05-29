package com.codearms.maoqiqi.one.home.fragment;

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
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.home.activity.ClassifyActivity;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;

import java.util.List;

public class FlowLayoutFragment extends LazyLoadFragment implements FlowLayoutContract.View {

    public static final String FROM_NAVIGATION = "FROM_NAVIGATION";
    public static final String FROM_KNOWLEDGE = "FROM_KNOWLEDGE";

    private FlowLayoutContract.Presenter presenter;

    private RecyclerView recyclerView;

    private String from;
    private List<NavigationBean> navigationBeans;

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
    public void setPresenter(FlowLayoutContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_flow_layout, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from", FROM_NAVIGATION);
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        switch (from) {
            case FROM_NAVIGATION:
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(new RecyclerViewAdapter(FROM_NAVIGATION, navigationBeans, null));
                break;
            case FROM_KNOWLEDGE:
                presenter.subscribe();
                break;
        }
    }

    @Override
    public void setKnowledge(List<ParentClassifyBean> parentClassifyBeans) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(FROM_KNOWLEDGE, null, parentClassifyBeans));
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private String from;
        private List<NavigationBean> navigationBeans;
        private List<ParentClassifyBean> parentClassifyBeans;
        private Chip chip;

        RecyclerViewAdapter(String from, List<NavigationBean> navigationBeans, List<ParentClassifyBean> parentClassifyBeans) {
            this.from = from;
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
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_item_flow_layout, null);
                chip.setText(bean.getArticleBeanList().get(j).getTitle());
                final int id = bean.getArticleBeanList().get(j).getId();
                final String url = bean.getArticleBeanList().get(j).getLink();
                chip.setOnClickListener(v -> WebViewActivity.start(context, id, url));
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
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_item_flow_layout, null);
                chip.setText(bean.getChildClassifyBeanList().get(j).getName());
                chip.setOnClickListener(v -> ClassifyActivity.start(context, bean));
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
}