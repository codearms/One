package com.codearms.maoqiqi.one.music.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.music.activity.MusicListActivity;
import com.codearms.maoqiqi.one.music.fragment.MusicListFragment;
import com.codearms.maoqiqi.one.utils.MediaLoader;
import com.codearms.maoqiqi.one.utils.MusicUtils;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-04 15:04
 */
public class MusicAdapter<T> extends BaseQuickAdapter<T, MusicAdapter.ViewHolder> {

    private Activity activity;
    private int type;
    private final List<Integer> integers;

    public MusicAdapter(int layoutResId, @Nullable List<T> data, Activity activity, int type, List<Integer> integers) {
        super(layoutResId, data);
        this.activity = activity;
        this.type = type;
        this.integers = integers;
    }

    @Override
    protected void convert(ViewHolder helper, T item) {
        String name;
        String imageUrl;
        switch (type) {
            case MusicListFragment.TYPE_SONG:// 歌曲
                MusicSongBean songBean = (MusicSongBean) item;
                imageUrl = MediaLoader.getAlbumArt(songBean.getAlbumId());
                if (imageUrl == null || imageUrl.equals("")) {
                    imageUrl = Constants.MUSICS[helper.getLayoutPosition() % Constants.MUSICS.length];
                }

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_song_placeholder).into(helper.ivMusic);
                helper.tvName.setText(songBean.getTitle());
                helper.tvInfo.setText(activity.getString(R.string.music_song_info, MusicUtils.getArtist(songBean.getArtist()), MusicUtils.getAlbum(songBean.getAlbum())));
                helper.ivMore.setOnClickListener(v -> {

                });
                helper.llItem.setOnClickListener(v -> {

                });
                break;
            case MusicListFragment.TYPE_ARTIST:// 艺术家
                MusicArtistBean artistBean = (MusicArtistBean) item;
                name = MusicUtils.getArtist(artistBean.getArtist());
                imageUrl = Constants.MUSICS[helper.getLayoutPosition() % Constants.MUSICS.length];

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_artist_placeholder).into(helper.ivMusic);
                helper.tvName.setText(MusicUtils.getArtist(artistBean.getArtist()));
                helper.tvInfo.setText(activity.getString(R.string.music_artist_info, artistBean.getNumberOfAlbums(), artistBean.getNumberOfTracks()));
                helper.ivMore.setOnClickListener(v -> {

                });
                helper.llItem.setTag(imageUrl);
                helper.llItem.setOnClickListener(v -> {
                    Pair<View, String> ivMusicPair = new Pair<>(helper.ivMusic, activity.getString(R.string.transition_music_img));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, ivMusicPair);
                    MusicListActivity.start(activity, name, (String) helper.llItem.getTag(), options.toBundle(), artistBean.get_id(), 0, null);
                });
                break;
            case MusicListFragment.TYPE_ALBUM:// 专辑
                MusicAlbumBean albumBean = (MusicAlbumBean) item;
                name = MusicUtils.getAlbum(albumBean.getAlbum());
                imageUrl = albumBean.getAlbumArt();
                if (imageUrl == null || imageUrl.equals("")) {
                    imageUrl = Constants.MUSICS[helper.getLayoutPosition() % Constants.MUSICS.length];
                }

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_album_placeholder).into(helper.ivMusic);
                helper.tvName.setText(name);
                helper.tvInfo.setText(activity.getString(R.string.music_album_info, MusicUtils.getArtist(albumBean.getArtist()), albumBean.getNumberOfSongs()));
                helper.ivMore.setOnClickListener(v -> {

                });
                helper.llItem.setTag(imageUrl);
                helper.llItem.setOnClickListener(v -> {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(helper.ivMusic, activity.getString(R.string.transition_music_img)));
                    MusicListActivity.start(activity, name, (String) helper.llItem.getTag(), options.toBundle(), 0, albumBean.get_id(), null);
                });
                break;
            case MusicListFragment.TYPE_FOLDER:// 文件夹
                String folderPath = (String) item;
                name = MusicUtils.getFolderName(folderPath);

                helper.ivMusic.setImageResource(R.drawable.ic_folder_placeholder);
                helper.tvName.setText(MusicUtils.getFolderName(folderPath));
                helper.tvInfo.setText(activity.getString(R.string.music_folder_info, integers.get(helper.getLayoutPosition()), MusicUtils.getPath(folderPath)));
                helper.ivMore.setOnClickListener(v -> {

                });
                helper.llItem.setOnClickListener(v -> MusicListActivity.start(activity, name, "", null, 0, 0, folderPath));
                break;
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        LinearLayout llItem;
        ImageView ivMusic;
        TextView tvName;
        TextView tvInfo;
        ImageView ivMore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            ivMusic = itemView.findViewById(R.id.iv_music);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}