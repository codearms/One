package com.codearms.maoqiqi.one.gank.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ItemBean;
import com.codearms.maoqiqi.one.gank.presenter.GankListPresenter;
import com.codearms.maoqiqi.one.gank.presenter.contract.GankListContract;
import com.codearms.maoqiqi.one.utils.RecyclerViewScrollListener;
import com.codearms.maoqiqi.one.utils.UrlMatch;
import com.codearms.maoqiqi.utils.DensityUtils;
import com.codearms.maoqiqi.utils.LogUtils;
import com.codearms.maoqiqi.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:04
 */
public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    private String category;

    private RecyclerView recyclerView;
    private List<ItemBean> list;

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

        recyclerView = rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getData(category, Constants.PAGE_INDEX, Constants.PAGE_COUNT);
    }

    @Override
    public void setData(List<ItemBean> list) {
        if (category.equals("福利")) {
            // 构造器中,第一个参数表示列数或者行数,第二个参数表示滑动方向,瀑布流
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            recyclerView.setLayoutManager(manager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            WelfareAdapter adapter = new WelfareAdapter(R.layout.item_gank_list_welfare, list);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerViewScrollListener(getContext()));
        } else {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);

            RecyclerAdapter adapter = new RecyclerAdapter(R.layout.item_gank_list, list);
            recyclerView.setAdapter(adapter);
        }
    }

    final class RecyclerAdapter extends BaseQuickAdapter<ItemBean, ViewHolder> {

        RecyclerAdapter(int layoutResId, @Nullable List<ItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, ItemBean item) {
            if (item.getType().equals("福利")) {
                helper.ivWelfare.setVisibility(View.VISIBLE);
                helper.layoutInfo.setVisibility(View.GONE);
                setImage(helper.ivWelfare, item.getUrl());
            } else {
                helper.ivWelfare.setVisibility(View.GONE);
                helper.layoutInfo.setVisibility(View.VISIBLE);
                helper.tvDes.setText(item.getDesc());
                if (item.getImages() != null && item.getImages().size() > 0 && !TextUtils.isEmpty(item.getImages().get(0))) {
                    helper.ivImage.setVisibility(View.VISIBLE);
                    setImage(helper.ivImage, item.getImages().get(0));
                } else {
                    helper.ivImage.setVisibility(View.GONE);
                }
            }

            setTag(helper.tvTag, item.getType(), item.getUrl());

            String name = item.getWho() == null ? "佚名" : item.getWho();
            helper.tvTime.setText(name + " " + getTranslateTime(item.getPublishedAt()));

            // 设置间距
            if (helper.getLayoutPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.cardView.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        private void setImage(ImageView imageView, String imageUrl) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }

        private void setTag(TextView tvTag, String type, String url) {
            if (type.equals("福利")) {
                tvTag.setVisibility(View.GONE);
                return;
            }
            String prefix = "";
            if (category.equals("all")) prefix = type + " · ";

            String key = UrlMatch.processUrl(url);
            GradientDrawable drawable = (GradientDrawable) tvTag.getBackground();
            if (UrlMatch.URL_2_CONTENT.containsKey(key)) {
                drawable.setColor(UrlMatch.URL_2_COLOR.get(key));
                tvTag.setText(prefix + UrlMatch.URL_2_CONTENT.get(key));
            } else {
                if (type.equals("休息视频")) {
                    drawable.setColor(UrlMatch.VIDEO_COLOR);
                    tvTag.setText(prefix + UrlMatch.VIDEO_CONTENT);
                } else {
                    // gitHub 的要特殊处理
                    if (url.contains(UrlMatch.GITHUB_PREFIX)) {
                        drawable.setColor(UrlMatch.GITHUB_COLOR);
                        tvTag.setText(prefix + UrlMatch.GITHUB_CONTENT);
                    } else {
                        drawable.setColor(UrlMatch.OTHER_BLOG_COLOR);
                        tvTag.setText(prefix + UrlMatch.OTHER_BLOG_CONTENT);
                    }
                }
            }
            tvTag.setVisibility(View.VISIBLE);
        }

        /**
         * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
         * 如果在今年的1天之外的只显示“月-日”,例如“05-03” 如果不是今年的显示“年-月-日”,例如“2014-03-11”
         *
         * @param time
         * @return
         */
        public String getTranslateTime(String time) {
            long timeMilliseconds = 0;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            // 在主页面中设置当天时间
            Date nowTime = new Date();
            String currDate = sdf1.format(nowTime);
            long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
            Date date = null;
            try {
                // 将给定的字符串中的日期提取出来
                date = sdf1.parse(time);
            } catch (Exception e) {
                e.printStackTrace();
                return time;
            }
            if (date != null) {
                timeMilliseconds = date.getTime();
            }

            long timeDifferent = currentMilliseconds - timeMilliseconds;


            if (timeDifferent < 60000) {// 一分钟之内

                return "刚刚";
            }
            if (timeDifferent < 3600000) {// 一小时之内
                long longMinute = timeDifferent / 60000;
                int minute = (int) (longMinute % 100);
                return minute + "分钟之前";
            }
            long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
            if (timeDifferent < l) {// 小于一天
                long longHour = timeDifferent / 3600000;
                int hour = (int) (longHour % 100);
                return hour + "小时之前";
            }
            if (timeDifferent >= l) {
                String currYear = currDate.substring(0, 4);
                String year = time.substring(0, 4);
                if (!year.equals(currYear)) {
                    return time.substring(0, 10);
                }
                return time.substring(5, 10);
            }
            return time;
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        CardView cardView;
        ImageView ivWelfare;
        LinearLayout layoutInfo;
        TextView tvDes;
        ImageView ivImage;
        TextView tvTag;
        TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            ivWelfare = itemView.findViewById(R.id.ivWelfare);
            layoutInfo = itemView.findViewById(R.id.layoutInfo);
            tvDes = itemView.findViewById(R.id.tvDes);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    final class WelfareAdapter extends BaseQuickAdapter<ItemBean, WelfareViewHolder> {

        private final int ivWidth;
        private final HashMap<Integer, Integer> hashMap;

        WelfareAdapter(int layoutResId, @Nullable List<ItemBean> data) {
            super(layoutResId, data);
            ivWidth = (ScreenUtils.getScreenWidth() - DensityUtils.dp2px(R.dimen.sixteen)) / 2;
            hashMap = new HashMap<>();
        }

        @Override
        protected void convert(WelfareViewHolder helper, ItemBean item) {
//            Integer viewHeight = hashMap.get(helper.getLayoutPosition());
//            if (viewHeight != null) {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) helper.ivWelfare.getLayoutParams();
//                layoutParams.height = viewHeight;
//                helper.ivWelfare.setLayoutParams(layoutParams);
//                Glide.with(context).load(item.getUrl()).into(helper.ivWelfare);
//            } else {
//                Glide.with(helper.ivWelfare.getContext()).asBitmap().load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        int ivHeight = resource.getHeight() * ivWidth / resource.getWidth();
//                        hashMap.put(helper.getLayoutPosition(), ivHeight);
//                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) helper.ivWelfare.getLayoutParams();
//                        layoutParams.height = ivHeight;
//                        helper.ivWelfare.setLayoutParams(layoutParams);
//                        helper.ivWelfare.setImageBitmap(resource);
//                    }
//                });
//            }
            LogUtils.e("info",item.getUrl());
            Glide.with(context).load(item.getUrl()).into(helper.ivWelfare);
//            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    static final class WelfareViewHolder extends BaseViewHolder {

        ImageView ivWelfare;

        WelfareViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWelfare = itemView.findViewById(R.id.ivWelfare);
        }
    }
}