package com.codearms.maoqiqi.one.gank.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ItemBean;
import com.codearms.maoqiqi.one.decoration.MarginItemDecoration;
import com.codearms.maoqiqi.one.gank.presenter.GankListPresenter;
import com.codearms.maoqiqi.one.gank.presenter.contract.GankListContract;
import com.codearms.maoqiqi.one.utils.GankUtils;
import com.codearms.maoqiqi.one.utils.RecyclerViewScrollListener;
import com.codearms.maoqiqi.one.utils.TimeUtils;
import com.codearms.maoqiqi.one.utils.UrlMatch;
import com.codearms.maoqiqi.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 干货列表数据
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-11 14:00
 */
public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    private String category;
    private int pageIndex = 1;

    private RecyclerAdapter adapter;
    private List<ItemBean> list = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();

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
        setRetainInstance(true);
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

        RecyclerView.LayoutManager manager;
        if (category.equals("福利")) {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelSize(R.dimen.four)));
        } else {
            manager = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelSize(R.dimen.twelve)));
        }

        adapter = new RecyclerAdapter(list);
//        adapter.setOnItemClickListener((adapter, view, position) -> {
//            imageUrls.clear();
//            for (ItemBean itemBean : list) {
//                imageUrls.add(itemBean.getUrl());
//            }
//            PictureActivity.start(context, imageUrls, position, null);
//        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener(getContext()));
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getData(category, pageIndex);
    }

    @Override
    public void setData(List<ItemBean> list) {
        this.list = list;
        adapter.replaceData(list);
    }

    final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ItemBean> data;
        // 保存图片Size的集合
        private ArrayMap<String, PictureSizeBean> map = new ArrayMap<>();

        RecyclerAdapter(List<ItemBean> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (category.equals("福利")) {
                return new WelfareViewHolder(View.inflate(context, R.layout.item_gank_list_welfare, null));
            } else {
                // View.inflate(context, R.layout.item_gank_list, null)); 布局Margin无效
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gank_list, viewGroup, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ItemBean item = data.get(i);

            if (viewHolder instanceof WelfareViewHolder) {
                WelfareViewHolder holder = (WelfareViewHolder) viewHolder;

                holder.tvWelfareTime.setText(item.getDesc());

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.ivWelfare.getLayoutParams();
                layoutParams.width = ScreenUtils.getScreenWidth() / 2;
                holder.ivWelfare.setLayoutParams(layoutParams);

                PictureSizeBean bean = map.get(item.getUrl());
                if (bean != null && !bean.isNull()) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                    params.height = (ScreenUtils.getScreenWidth() / 2) * bean.getHeight() / bean.getWidth();
                }

                Glide.with(holder.ivWelfare.getContext()).asBitmap().load(item.getUrl())
//                    .thumbnail(0.2f)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                PictureSizeBean bean = map.get(item.getUrl());
                                if (bean != null && !bean.isNull()) {
                                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                                    params.height = (ScreenUtils.getScreenWidth() / 2) * bean.getHeight() / bean.getWidth();
                                    map.put(item.getUrl(), new PictureSizeBean(bean.getWidth(), bean.getHeight()));
                                }
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.ivWelfare);
            } else {
                ViewHolder holder = (ViewHolder) viewHolder;

                holder.tvTitle.setText(item.getDesc());

                if (item.getImages() != null && item.getImages().size() > 0 && !TextUtils.isEmpty(item.getImages().get(0))) {
                    holder.ivImage.setVisibility(View.VISIBLE);
                    setImage(holder.ivImage, item.getImages().get(0));
                } else {
                    holder.ivImage.setVisibility(View.GONE);
                }

                if (item.getType().equals("")) {

                }

                setTag(holder.tvTag, item.getType(), item.getUrl());

                holder.tvAuthor.setText(GankUtils.getWho(item.getWho()));
                holder.tvTime.setText(TimeUtils.getTranslateTime(item.getPublishedAt()));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void setImage(ImageView imageView, String imageUrl) {
            Glide.with(imageView.getContext()).asBitmap().load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

        private void setTag(TextView tvTag, String type, String url) {
            String key = UrlMatch.processUrl(url);

            GradientDrawable drawable = (GradientDrawable) tvTag.getBackground();
            drawable.setColor(UrlMatch.getColor(type, key));

            String prefix = category.equals("all") ? type + " · " : "";
            tvTag.setText(String.format("%s%s", prefix, UrlMatch.getContent(type, key)));
        }

        void replaceData(@NonNull Collection<ItemBean> list) {
            // 不是同一个引用才清空列表
            if (list != data) {
                data.clear();
                data.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    private static final class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        CardView cardView;
        TextView tvTitle;
        ImageView ivImage;
        ImageView ivCategory;
        TextView tvTag;
        TextView tvAuthor;
        TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivCategory = itemView.findViewById(R.id.iv_category);
            tvTag = itemView.findViewById(R.id.tv_tag);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvTime = itemView.findViewById(R.id.tv_date);
        }
    }

    private static final class WelfareViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivWelfare;
        TextView tvWelfareTime;

        WelfareViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivWelfare = itemView.findViewById(R.id.iv_welfare);
            tvWelfareTime = itemView.findViewById(R.id.tv_welfare_time);
        }
    }

    // 加载图片缓存宽高,防止出现滑动图片改变高度问题
    private class PictureSizeBean {
        private String url;
        private int width;
        private int height;

        PictureSizeBean(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isNull() {
            return width == 0 || height == 0;
        }
    }
}