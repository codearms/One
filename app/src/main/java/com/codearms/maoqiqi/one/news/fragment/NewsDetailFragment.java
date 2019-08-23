package com.codearms.maoqiqi.one.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;
import com.codearms.maoqiqi.one.news.presenter.NewsDetailPresenter;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsDetailContract;
import com.codearms.maoqiqi.one.utils.AppBarStateChangeListener;
import com.codearms.maoqiqi.utils.ToastUtils;

/**
 * 新闻详情
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-17 18:34
 */
public class NewsDetailFragment extends BaseFragment<NewsDetailContract.Presenter> implements NewsDetailContract.View {

    private static final String MIME_TYPE = "text/html; charset=utf-8";
    private static final String ENCODING = "utf-8";

    private int id;

    private ImageView ivHeader;
    private TextView tvSource;
    private WebView webView;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsDetailFragment.
     */
    public static NewsDetailFragment newInstance(int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        presenter = new NewsDetailPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle == null) return;

        id = bundle.getInt("id");
        String title = bundle.getString("title");

        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar_layout);
        ivHeader = rootView.findViewById(R.id.iv_header);
        TextView tvNewsTitle = rootView.findViewById(R.id.tv_news_title);
        tvSource = rootView.findViewById(R.id.tv_source);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        webView = rootView.findViewById(R.id.web_view);

        ((AppCompatActivity) context).setSupportActionBar(toolbar);

        tvTitle.setText(title);
        tvNewsTitle.setText(title);

        // 使用CollapsingToolbarLayout的时候要注意,在完成CollapsingToolbarLayout设置之后再调用Toolbar的setTitle()等方法将没有效果,
        // 我们需要改为调用CollapsingToolbarLayout的setTitle()等方法来对工具栏进行修改。
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) { // 折叠状态
                    tvTitle.setVisibility(View.VISIBLE);
                } else if (state == State.EXPANDED) { // 展开状态
                    tvTitle.setVisibility(View.INVISIBLE);
                } else {// 中间状态
                    tvTitle.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getNewsDetail(id);
    }

    @Override
    public void setNewsDetail(NewsDetailBean newsDetailBean) {
        Glide.with(context).load(newsDetailBean.getImage()).into(ivHeader);
        tvSource.setText(newsDetailBean.getImageSource());

        StringBuilder buffer = new StringBuilder();
        buffer.append("<html lang=\"zh-CN\">");
        buffer.append("<head>");
        buffer.append("<meta name=\"viewport\" content=\"user-scalable=no, width=device-width\">");
        for (int i = 0; i < newsDetailBean.getCss().size(); i++) {
            buffer.append("<link rel=\"stylesheet\" href=\"").append(newsDetailBean.getCss().get(i)).append("\">");
        }
        buffer.append("<style>");
        buffer.append(".headline { border-bottom: 0px solid #f6f6f6;}");
        buffer.append(".headline .img-place-holder { height: 0px;}");
        buffer.append("</style>");
        buffer.append("</head>");
        buffer.append("<body>");
        buffer.append(newsDetailBean.getBody());
        buffer.append("</body>");
        buffer.append("</html>");

        webView.loadData(buffer.toString(), MIME_TYPE, ENCODING);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_share) {
            ToastUtils.show("share");
            return true;
        }
        return false;
    }
}