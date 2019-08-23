package com.codearms.maoqiqi.one.book.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.book.presenter.BookDetailPresenter;
import com.codearms.maoqiqi.one.book.presenter.contract.BookDetailContract;
import com.codearms.maoqiqi.one.data.bean.BookDetailBean;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.utils.BookUtils;
import com.codearms.maoqiqi.one.view.SummaryView;

/**
 * 图书详情
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-20 17:48
 */
public class BookDetailFragment extends BaseFragment<BookDetailContract.Presenter> implements BookDetailContract.View {

    private String id;

    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookPublisher;
    TextView tvBookPublishDate;
    TextView tvBookPrice;
    TextView tvBookRating;
    RatingBar ratingBar;
    TextView tvBookNumRaters;
    SummaryView summaryView;
    SummaryView summaryViewAuthor;
    TextView tvBookCatalog;

    private BookDetailBean bookDetailBean;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookDetailFragment.
     */
    public static BookDetailFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        presenter = new BookDetailPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;

        id = bundle.getString("id");

        tvBookTitle = rootView.findViewById(R.id.tv_book_title);
        tvBookAuthor = rootView.findViewById(R.id.tv_book_author);
        tvBookPublisher = rootView.findViewById(R.id.tv_book_publisher);
        tvBookPublishDate = rootView.findViewById(R.id.tv_book_publish_date);
        tvBookPrice = rootView.findViewById(R.id.tv_book_price);

        tvBookRating = rootView.findViewById(R.id.tv_book_rating);
        ratingBar = rootView.findViewById(R.id.rating_bar);
        tvBookNumRaters = rootView.findViewById(R.id.tv_book_ratings_count);

        summaryView = rootView.findViewById(R.id.summary_view);
        summaryViewAuthor = rootView.findViewById(R.id.summary_view_author);
        tvBookCatalog = rootView.findViewById(R.id.tv_book_catalog);

        if (savedInstanceState != null && bookDetailBean != null) {
            setBookDetail(bookDetailBean);
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getBookDetail(id);
    }

    @Override
    public void setBookDetail(BookDetailBean bookDetailBean) {
        loadDataCompleted();
        this.bookDetailBean = bookDetailBean;

        tvBookTitle.setText(bookDetailBean.getTitle());
        tvBookAuthor.setText(getString(R.string.book_author, BookUtils.formatAuthor(bookDetailBean.getAuthor())));
        tvBookPublisher.setText(getString(R.string.book_publisher, bookDetailBean.getPublisher()));
        tvBookPublishDate.setText(getString(R.string.book_publish_date, bookDetailBean.getPubDate()));
        tvBookPrice.setText(getString(R.string.book_price, bookDetailBean.getPrice()));

        tvBookRating.setText(bookDetailBean.getRatingBean().getAverage());
        ratingBar.setRating(Float.parseFloat(bookDetailBean.getRatingBean().getAverage()) / 2);
        tvBookNumRaters.setText(getString(R.string.num_raters, bookDetailBean.getRatingBean().getNumRaters()));

        summaryView.setText(bookDetailBean.getSummary());
        summaryViewAuthor.setText(bookDetailBean.getAuthorIntro());
        tvBookCatalog.setText(bookDetailBean.getCatalog());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_share:
                return true;
            case R.id.menu_more:
                if (bookDetailBean != null && bookDetailBean.getAlt() != null) {
                    WebViewActivity.start(context, 2, bookDetailBean.getAlt());
                }
                break;
        }
        return false;
    }
}