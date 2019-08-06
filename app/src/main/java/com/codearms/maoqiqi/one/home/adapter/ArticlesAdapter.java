package com.codearms.maoqiqi.one.home.adapter;

import android.app.Application;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.home.activity.ClassifyActivity;
import com.codearms.maoqiqi.one.home.fragment.ArticlesFragment;

import java.util.List;

public class ArticlesAdapter extends BaseQuickAdapter<ArticleBean, ArticlesAdapter.ViewHolder> {

    private int topArticles;
    private String from;
    private boolean isClassify;

    private final Application application;
    private final String just;
    private final String minute;
    private final String hour;
    private final String oneDay;

    public ArticlesAdapter(int layoutResId, @Nullable List<ArticleBean> data, int topArticles, String from, boolean isClassify) {
        super(layoutResId, data);
        this.topArticles = topArticles;
        this.from = from;
        this.isClassify = isClassify;
        this.application = App.getInstance();
        this.just = application.getString(R.string.just);
        this.minute = application.getString(R.string.minute);
        this.hour = application.getString(R.string.hour);
        this.oneDay = application.getString(R.string.one_day);
    }

    @Override
    protected void convert(ViewHolder helper, ArticleBean articleBean) {
        // 置顶
        helper.ivTop.setVisibility(helper.getLayoutPosition() < topArticles ? View.VISIBLE : View.GONE);
        // 图片
        if (articleBean.getEnvelopePic() == null || articleBean.getEnvelopePic().equals("")) {
            helper.ivProject.setVisibility(View.GONE);
        } else {
            helper.ivProject.setVisibility(View.VISIBLE);
            Glide.with(application).load(articleBean.getEnvelopePic())
                    .placeholder(R.drawable.ic_article_placeholder).into(helper.ivProject);
        }

        // 标题
        helper.tvTitle.setText(Html.fromHtml(articleBean.getTitle()));
        // 是否收藏
        if (from.equals(ArticlesFragment.FROM_COLLECT)) {
            helper.ivCollect.setImageResource(R.drawable.ic_collect);
            setTintList(helper.ivCollect);
        } else {
            helper.ivCollect.setImageResource(articleBean.isCollect() ? R.drawable.ic_collect : R.drawable.ic_un_collect);
        }

        // 描述
        if (articleBean.getDesc() == null || articleBean.getDesc().equals("")) {
            helper.tvDes.setVisibility(View.GONE);
        } else {
            helper.tvDes.setVisibility(View.VISIBLE);
            helper.tvDes.setText(Html.fromHtml(articleBean.getDesc()));
        }

        // 是否是新的
        if (articleBean.getNiceDate().contains(just)
                || articleBean.getNiceDate().contains(minute)
                || articleBean.getNiceDate().contains(hour)
                || articleBean.getNiceDate().contains(oneDay)) {
            helper.tvNew.setVisibility(View.VISIBLE);
        } else {
            helper.tvNew.setVisibility(View.GONE);
        }

        if (articleBean.getTags() != null && articleBean.getTags().size() > 0) {
            TagAdapter tagAdapter = new TagAdapter(R.layout.item_tag, articleBean.getTags(), isClassify, helper.getLayoutPosition());
            tagAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                // 跳转到分类
                int index = (int) view.getTag();
                ClassifyActivity.start(view.getContext(), from, getData().get(index));
            });

            helper.recyclerView.setLayoutManager(new LinearLayoutManager(application));
            helper.recyclerView.setItemAnimator(new DefaultItemAnimator());
            helper.recyclerView.setNestedScrollingEnabled(false);
            helper.recyclerView.setHasFixedSize(true);
            helper.recyclerView.setAdapter(tagAdapter);

            helper.recyclerView.setVisibility(View.VISIBLE);
            helper.tvClassify.setVisibility(View.GONE);
        } else {
            helper.recyclerView.setVisibility(View.GONE);
            helper.tvClassify.setVisibility(View.VISIBLE);
            if (from.equals(ArticlesFragment.FROM_COLLECT)) {
                helper.tvClassify.setText(articleBean.getChapterName());
            } else {
                helper.tvClassify.setText(String.format("%s/%s", articleBean.getSuperChapterName(), articleBean.getChapterName()));
                if (isClassify) helper.addOnClickListener(R.id.tv_classify);
            }
        }

        // 作者
        helper.tvAuthor.setText(articleBean.getAuthor());
        // 时间
        helper.tvDate.setText(articleBean.getNiceDate());

        // 设置事件
        helper.addOnClickListener(R.id.card_view);
        helper.addOnClickListener(R.id.iv_project);
        helper.addOnClickListener(R.id.iv_collect);
    }

    private void setTintList(ImageView imageView) {
        Drawable tintIcon = DrawableCompat.wrap(imageView.getDrawable());
        ColorStateList csl = application.getResources().getColorStateList(R.color.selector_collect_navigation);
        DrawableCompat.setTintList(tintIcon, csl);
        imageView.setImageDrawable(tintIcon);
    }

    public void setTopArticles(int topArticles) {
        this.topArticles = topArticles;
    }

    final class ViewHolder extends BaseViewHolder {

        CardView cardView;
        ImageView ivTop;
        ImageView ivProject;
        TextView tvTitle;
        ImageView ivCollect;
        TextView tvDes;
        TextView tvNew;
        RecyclerView recyclerView;
        TextView tvClassify;
        TextView tvAuthor;
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            ivTop = itemView.findViewById(R.id.iv_top);
            ivProject = itemView.findViewById(R.id.iv_project);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivCollect = itemView.findViewById(R.id.iv_collect);
            tvDes = itemView.findViewById(R.id.tv_des);
            tvNew = itemView.findViewById(R.id.tv_new);
            tvClassify = itemView.findViewById(R.id.tv_classify);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    static final class TagAdapter extends BaseQuickAdapter<ArticleBean.TagBean, ArticlesAdapter.TagViewHolder> {

        private boolean isClassify;
        private int position;

        TagAdapter(int layoutResId, @Nullable List<ArticleBean.TagBean> data, boolean isClassify, int position) {
            super(layoutResId, data);
            this.isClassify = isClassify;
            this.position = position;
        }

        @Override
        protected void convert(TagViewHolder helper, ArticleBean.TagBean item) {
            helper.tvName.setText(item.getName());
            helper.tvName.setTag(position);
            if (isClassify) helper.addOnClickListener(R.id.tv_name);
        }
    }

    static final class TagViewHolder extends BaseViewHolder {

        TextView tvName;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}