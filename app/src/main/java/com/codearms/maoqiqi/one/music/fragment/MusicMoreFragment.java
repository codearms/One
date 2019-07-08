package com.codearms.maoqiqi.one.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicMoreItemBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.utils.MusicUtils;
import com.codearms.maoqiqi.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-05 14:07
 */
public class MusicMoreFragment extends DialogFragment {

    private View view;
    private TextView tvTitle;
    private RecyclerView recyclerView;

    private int type;
    private MusicSongBean songBean;
    private MusicArtistBean artistBean;
    private MusicAlbumBean albumBean;
    private String folderPath;

    private List<MusicMoreItemBean> list = new ArrayList<>();

    public static MusicMoreFragment newInstance(MusicSongBean songBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_SONG);
        bundle.putSerializable("songBean", songBean);
        MusicMoreFragment fragment = new MusicMoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MusicMoreFragment newInstance(MusicArtistBean artistBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_ARTIST);
        bundle.putSerializable("artistBean", artistBean);
        MusicMoreFragment fragment = new MusicMoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MusicMoreFragment newInstance(MusicAlbumBean albumBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_ALBUM);
        bundle.putSerializable("albumBean", albumBean);
        MusicMoreFragment fragment = new MusicMoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MusicMoreFragment newInstance(String folderPath) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_FOLDER);
        bundle.putString("folderPath", folderPath);
        MusicMoreFragment fragment = new MusicMoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_music_more, container, false);
            tvTitle = view.findViewById(R.id.tv_title);
            recyclerView = view.findViewById(R.id.recycler_view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(() -> {
            if (getDialog() == null || getDialog().getWindow() == null) return;
            Window window = getDialog().getWindow();

            window.setWindowAnimations(R.style.DialogSheetAnimation);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            int halfScreenHeight = ScreenUtils.getScreenHeight() / 2;
            params.height = view.getHeight() > halfScreenHeight ?
                    halfScreenHeight : WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;

        type = bundle.getInt("type");
        switch (type) {
            case MusicListFragment.TYPE_SONG:
                songBean = (MusicSongBean) bundle.getSerializable("songBean");
                break;
            case MusicListFragment.TYPE_ARTIST:
                artistBean = (MusicArtistBean) bundle.getSerializable("artistBean");
                break;
            case MusicListFragment.TYPE_ALBUM:
                albumBean = (MusicAlbumBean) bundle.getSerializable("albumBean");
                break;
            case MusicListFragment.TYPE_FOLDER:
                folderPath = bundle.getString("folderPath");
                break;
        }
        setData();
    }

    private void setData() {
        switch (type) {
            case MusicListFragment.TYPE_SONG:
                tvTitle.setText(getString(R.string.song_info, songBean.getTitle()));

                list.add(new MusicMoreItemBean(getString(R.string.play_next), R.drawable.ic_more_play_next));
                list.add(new MusicMoreItemBean(getString(R.string.collect_song_list), R.drawable.ic_more_collect_song_list));
                list.add(new MusicMoreItemBean(getString(R.string.share), R.drawable.ic_more_share));
                list.add(new MusicMoreItemBean(getString(R.string.artist_info, MusicUtils.getArtist(songBean.getArtist())), R.drawable.ic_more_artist));
                list.add(new MusicMoreItemBean(getString(R.string.album_info, MusicUtils.getAlbum(songBean.getAlbum())), R.drawable.ic_more_album));
                list.add(new MusicMoreItemBean(getString(R.string.set_ring), R.drawable.ic_more_set_ring));
                list.add(new MusicMoreItemBean(getString(R.string.song_information), R.drawable.ic_more_song_information));
                list.add(new MusicMoreItemBean(getString(R.string.delete), R.drawable.ic_more_delete));
                break;
            case MusicListFragment.TYPE_ARTIST:
                tvTitle.setText(getString(R.string.artist_info, MusicUtils.getArtist(artistBean.getArtist())));

                list.add(new MusicMoreItemBean(getString(R.string.play_next), R.drawable.ic_more_play_next));
                list.add(new MusicMoreItemBean(getString(R.string.collect_song_list), R.drawable.ic_more_collect_song_list));
                list.add(new MusicMoreItemBean(getString(R.string.delete), R.drawable.ic_more_delete));
                break;
            case MusicListFragment.TYPE_ALBUM:
                tvTitle.setText(getString(R.string.album_info, MusicUtils.getAlbum(albumBean.getAlbum())));

                list.add(new MusicMoreItemBean(getString(R.string.play_next), R.drawable.ic_more_play_next));
                list.add(new MusicMoreItemBean(getString(R.string.collect_song_list), R.drawable.ic_more_collect_song_list));
                list.add(new MusicMoreItemBean(getString(R.string.delete), R.drawable.ic_more_delete));
                break;
            case MusicListFragment.TYPE_FOLDER:
                tvTitle.setText(getString(R.string.folder_info, MusicUtils.getFolderName(folderPath)));

                list.add(new MusicMoreItemBean(getString(R.string.play_next), R.drawable.ic_more_play_next));
                list.add(new MusicMoreItemBean(getString(R.string.collect_song_list), R.drawable.ic_more_collect_song_list));
                list.add(new MusicMoreItemBean(getString(R.string.delete), R.drawable.ic_more_delete));
                break;
        }

        RecyclerAdapter adapter = new RecyclerAdapter(R.layout.item_music_more, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    final class RecyclerAdapter extends BaseQuickAdapter<MusicMoreItemBean, ViewHolder> {

        RecyclerAdapter(int layoutResId, @Nullable List<MusicMoreItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, MusicMoreItemBean item) {
            helper.ivIcon.setImageResource(item.getImageId());
            helper.tvTitle.setText(item.getTitle());
            helper.rlItem.setOnClickListener(v -> {

            });
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        RelativeLayout rlItem;
        ImageView ivIcon;
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItem = itemView.findViewById(R.id.rl_item);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}