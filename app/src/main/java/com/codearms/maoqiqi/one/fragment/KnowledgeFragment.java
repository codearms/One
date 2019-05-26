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
import com.codearms.maoqiqi.one.data.bean.KnowledgeBean;
import com.codearms.maoqiqi.one.presenter.contract.KnowledgeContract;

import java.util.List;

public class KnowledgeFragment extends LazyLoadFragment implements KnowledgeContract.View {

    private KnowledgeContract.Presenter presenter;

    private RecyclerView recyclerView;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment KnowledgeFragment.
     */
    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    @Override
    public void setPresenter(KnowledgeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_knowledge, container, false);
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
    public void setKnowledge(List<KnowledgeBean> knowledgeBeans) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(knowledgeBeans));
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<KnowledgeBean> knowledgeBeans;
        private Chip chip;

        RecyclerViewAdapter(List<KnowledgeBean> knowledgeBeans) {
            this.knowledgeBeans = knowledgeBeans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_knowledge, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final KnowledgeBean knowledgeBean = knowledgeBeans.get(i);
            viewHolder.tvName.setText(knowledgeBean.getName());

            for (int j = 0; j < knowledgeBean.getChildren().size(); j++) {
                chip = (Chip) LayoutInflater.from(context).inflate(R.layout.item_item_knowledge_content, null);
                chip.setText(knowledgeBean.getChildren().get(j).getName());
                viewHolder.chipGroup.addView(chip);
            }

            if (i == knowledgeBeans.size() - 1) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.chipGroup.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        @Override
        public int getItemCount() {
            return knowledgeBeans.size();
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