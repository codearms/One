package com.codearms.maoqiqi.one.book.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.book.activity.BookDetailActivity;
import com.codearms.maoqiqi.one.book.presenter.BookListPresenter;
import com.codearms.maoqiqi.one.book.presenter.contract.BookListContract;
import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.decoration.DividerDecoration;

import java.util.List;

public class BookListFragment extends BaseFragment<BookListContract.Presenter> implements BookListContract.View {

    private List<BookListBean.BookBean> list;
    private RecyclerAdapter adapter;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookListFragment.
     */
    public static BookListFragment newInstance(String keyword, String bookTag) {
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        bundle.putString("bookTag", bookTag);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BookListPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        adapter = new RecyclerAdapter(R.layout.item_book_list, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BookListBean.BookBean item = list.get(position);
                ImageView ivBook = (ImageView) adapter.getViewByPosition(recyclerView, position, R.id.iv_book);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivBook, ViewCompat.getTransitionName(ivBook));
                BookDetailActivity.start(context, item.getId(), item.getTitle(), item.getImageBean().getLarge(), options.toBundle());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerDecoration());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        Bundle bundle = getArguments();
        presenter.getBook(bundle.getString("keyword"), bundle.getString("bookTag"), Constants.PAGE_INDEX, Constants.PAGE_COUNT);
    }

    @Override
    public void setBook(BookListBean bookListBean) {
        list = bookListBean.getBookBeanList();
        adapter.replaceData(list);
    }

    static final class RecyclerAdapter extends BaseQuickAdapter<BookListBean.BookBean, ViewHolder> {

        RecyclerAdapter(int layoutResId, @Nullable List<BookListBean.BookBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, BookListBean.BookBean item) {
            Glide.with(helper.ivBook.getContext()).load(item.getImageBean().getLarge()).placeholder(R.drawable.ic_book_placeholder).into(helper.ivBook);

            helper.tvBookTitle.setText(item.getTitle());
            if (item.getAuthorList() != null && item.getAuthorList().size() > 0) {
                helper.tvBookAuthor.setVisibility(View.VISIBLE);
                helper.tvBookAuthor.setText(item.getAuthorList().get(0));
            } else {
                helper.tvBookAuthor.setVisibility(View.GONE);
            }
            helper.tvBookPublish.setText(item.getPublisher() + " / " + item.getPubDate());
            if (item.getPrice().equals("")) {
                helper.tvBookPrice.setVisibility(View.GONE);
            } else {
                helper.tvBookPrice.setVisibility(View.VISIBLE);
                helper.tvBookPrice.setText(item.getPrice());
            }
            helper.ratingBar.setRating(Float.parseFloat(item.getRatingBean().getAverage()) / 2);
            helper.tvBookRating.setText(item.getRatingBean().getAverage());
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        LinearLayout llItem;
        ImageView ivBook;
        TextView tvBookTitle;
        TextView tvBookAuthor;
        TextView tvBookPublish;
        TextView tvBookPrice;
        RatingBar ratingBar;
        TextView tvBookRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            ivBook = itemView.findViewById(R.id.iv_book);
            tvBookTitle = itemView.findViewById(R.id.tv_book_title);
            tvBookAuthor = itemView.findViewById(R.id.tv_book_author);
            tvBookPublish = itemView.findViewById(R.id.tv_book_publish);
            tvBookPrice = itemView.findViewById(R.id.tv_book_price);
            ratingBar = itemView.findViewById(R.id.rating_rar);
            tvBookRating = itemView.findViewById(R.id.tv_book_rating);
        }
    }
}