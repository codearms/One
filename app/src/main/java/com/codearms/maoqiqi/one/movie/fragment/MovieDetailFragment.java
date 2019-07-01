package com.codearms.maoqiqi.one.movie.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;
import com.codearms.maoqiqi.one.decoration.SpaceItemDecoration;
import com.codearms.maoqiqi.one.movie.presenter.MovieDetailPresenter;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieDetailContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.utils.MovieUtils;
import com.codearms.maoqiqi.one.view.SummaryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 10:41
 */
public class MovieDetailFragment extends BaseFragment<MovieDetailContract.Presenter> implements MovieDetailContract.View {

    private String id;

    private TextView tvMovieTitle;
    private TextView tvMovieGenre;
    private TextView tvMovieDuration;
    private TextView tvMovieMainLandPubDate;
    private TextView tvCountry;

    private TextView tvMovieRating;
    private RatingBar ratingBar;
    private TextView tvMovieRatingsCount;

    private SummaryView summaryView;

    private PersonRecyclerAdapter personRecyclerAdapter;
    private List<MovieDetailBean.PersonBean> personList = new ArrayList<>();
    private int directorsPersonNum;

    private ImageRecyclerAdapter imageRecyclerAdapter;
    private MovieDetailBean.VideoBean videoBean;
    private List<MovieDetailBean.PhotoBean> photoBeanList;
    private List<String> imageList = new ArrayList<>();

    private String url = "";

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovieDetailPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;

        id = bundle.getString("id");

        tvMovieTitle = rootView.findViewById(R.id.tv_movie_title);
        tvMovieGenre = rootView.findViewById(R.id.tv_movie_genre);
        tvMovieDuration = rootView.findViewById(R.id.tv_movie_duration);
        tvMovieMainLandPubDate = rootView.findViewById(R.id.tv_movie_main_land_pub_date);
        tvCountry = rootView.findViewById(R.id.tv_country);

        tvMovieRating = rootView.findViewById(R.id.tv_movie_rating);
        ratingBar = rootView.findViewById(R.id.rating_bar);
        tvMovieRatingsCount = rootView.findViewById(R.id.tv_movie_ratings_count);

        summaryView = rootView.findViewById(R.id.summary_view);

        RecyclerView recyclerViewPerson = rootView.findViewById(R.id.recycler_view_person);
        RecyclerView recyclerViewImage = rootView.findViewById(R.id.recycler_view_image);

        personRecyclerAdapter = new PersonRecyclerAdapter(R.layout.item_movie_person_list, personList);
        personRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> WebViewActivity.start(context, 4, personList.get(position).getAlt()));

        imageRecyclerAdapter = new ImageRecyclerAdapter(R.layout.item_movie_image_list, imageList);

        recyclerViewPerson.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerViewPerson.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPerson.setNestedScrollingEnabled(false);
        recyclerViewPerson.setHasFixedSize(true);
        recyclerViewPerson.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.sixteen)));
        recyclerViewPerson.setAdapter(personRecyclerAdapter);

        recyclerViewImage.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImage.setNestedScrollingEnabled(false);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.four)));
        recyclerViewImage.setAdapter(imageRecyclerAdapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getMovieDetail(id);
    }

    @Override
    public void setMovieDetail(MovieDetailBean detailBean) {
        url = detailBean.getShareUrl();

        tvMovieTitle.setText(detailBean.getTitle());
        tvMovieGenre.setText(String.format(getString(R.string.movie_genre), MovieUtils.formatGenre(detailBean.getGenres())));
        tvMovieDuration.setText(String.format(getString(R.string.movie_duration), MovieUtils.formatDuration(detailBean.getDurations())));
        tvMovieMainLandPubDate.setText(String.format(getString(R.string.movie_year), detailBean.getMainLandPubDate()));
        tvCountry.setText(String.format(getString(R.string.country), MovieUtils.formatCountry(detailBean.getCountries())));

        ratingBar.setRating((float) (detailBean.getRatingBean().getAverage() / 2));
        tvMovieRating.setText(String.valueOf(detailBean.getRatingBean().getAverage()));
        tvMovieRatingsCount.setText(String.format(getString(R.string.movie_num_raters), detailBean.getRatingsCount()));

        summaryView.setText(detailBean.getSummary());

        personList.clear();
        personList.addAll(detailBean.getDirectorsPersonBeanList());
        personList.addAll(detailBean.getCastPersonBeanList());
        directorsPersonNum = detailBean.getDirectorsPersonBeanList().size();
        personRecyclerAdapter.replaceData(personList);

        videoBean = detailBean.getTrailerVideoBeanList().get(0);
        imageList.add(0, null);
        photoBeanList = detailBean.getPhotoBeanList();
        for (MovieDetailBean.PhotoBean bean : photoBeanList) {
            imageList.add(bean.getThumb());
        }
        imageRecyclerAdapter.replaceData(imageList);
    }

    @Override
    public void moreInfo() {

    }

    final class PersonRecyclerAdapter extends BaseQuickAdapter<MovieDetailBean.PersonBean, PersonViewHolder> {

        PersonRecyclerAdapter(int layoutResId, @Nullable List<MovieDetailBean.PersonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(PersonViewHolder helper, MovieDetailBean.PersonBean item) {
            Glide.with(helper.ivMovieActor.getContext()).load(item.getImageBean().getLarge())
                    .placeholder(R.drawable.ic_picture_placeholder).into(helper.ivMovieActor);
            helper.tvMovieActorName.setText(item.getName());
            helper.tvMovieActorTypeName.setText(directorsPersonNum <= helper.getLayoutPosition() ? R.string.movie_star : R.string.movie_director);
        }
    }

    static final class PersonViewHolder extends BaseViewHolder {

        ImageView ivMovieActor;
        TextView tvMovieActorName;
        TextView tvMovieActorTypeName;

        PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovieActor = itemView.findViewById(R.id.iv_movie_actor);
            tvMovieActorName = itemView.findViewById(R.id.tv_movie_actor_name);
            tvMovieActorTypeName = itemView.findViewById(R.id.tv_movie_actor_type_name);
        }
    }

    final class ImageRecyclerAdapter extends BaseQuickAdapter<String, ImageViewHolder> {

        private Application application;

        ImageRecyclerAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
            application = App.getInstance();
        }

        @Override
        protected void convert(ImageViewHolder helper, String item) {
            String url;
            if (helper.getLayoutPosition() == 0) {
                url = videoBean.getMedium();
                helper.ivPlayVideo.setVisibility(View.VISIBLE);
            } else {
                url = photoBeanList.get(helper.getLayoutPosition() - 1).getThumb();
                helper.ivPlayVideo.setVisibility(View.GONE);
            }
            Glide.with(helper.ivMovieImage.getContext()).load(url)
                    .placeholder(R.drawable.ic_movie_placeholder).into(helper.ivMovieImage);

            // 设置间距
            if (helper.getLayoutPosition() == 0) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.frameLayout.getLayoutParams();
                params.leftMargin = application.getResources().getDimensionPixelSize(R.dimen.twelve);
            } else if (helper.getLayoutPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.frameLayout.getLayoutParams();
                params.rightMargin = application.getResources().getDimensionPixelSize(R.dimen.twelve);
            }
        }
    }

    static final class ImageViewHolder extends BaseViewHolder {

        FrameLayout frameLayout;
        ImageView ivMovieImage;
        ImageView ivPlayVideo;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_layout);
            ivMovieImage = itemView.findViewById(R.id.iv_movie_image);
            ivPlayVideo = itemView.findViewById(R.id.iv_play_video);
        }
    }
}