package com.codearms.maoqiqi.one.music.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.music.activity.MusicListActivity;
import com.codearms.maoqiqi.one.music.activity.MusicMoreActivity;
import com.codearms.maoqiqi.one.music.fragment.MusicListFragment;
import com.codearms.maoqiqi.one.music.fragment.MusicMoreFragment;
import com.codearms.maoqiqi.one.utils.MediaLoader;
import com.codearms.maoqiqi.one.utils.MusicUtils;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private static final String TAG_SONG = "com.codearms.maoqiqi.one.SONG";

    private FragmentActivity activity;
    private int type;

    private List<MusicSongBean> musicSongBeanList;
    private List<MusicArtistBean> musicArtistBeanList;
    private List<MusicAlbumBean> musicAlbumBeanList;
    private List<String> folderList;
    private List<Integer> integers;

    public MusicAdapter(FragmentActivity activity, int type) {
        this.activity = activity;
        this.type = type;
    }

    public void setSongList(List<MusicSongBean> musicSongBeanList) {
        this.musicSongBeanList = musicSongBeanList;
        notifyDataSetChanged();
    }

    public void setArtistList(List<MusicArtistBean> musicArtistBeanList) {
        this.musicArtistBeanList = musicArtistBeanList;
        notifyDataSetChanged();
    }

    public void setAlbumList(List<MusicAlbumBean> musicAlbumBeanList) {
        this.musicAlbumBeanList = musicAlbumBeanList;
        notifyDataSetChanged();
    }

    public void setFolderList(List<String> folderList, List<Integer> integers) {
        this.folderList = folderList;
        this.integers = integers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_music_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name;
        String imageUrl;
        switch (type) {
            case MusicListFragment.TYPE_SONG:// 歌曲
                MusicSongBean songBean = musicSongBeanList.get(i);
                imageUrl = MediaLoader.getAlbumArt(songBean.getAlbumId());
                if (imageUrl == null || imageUrl.equals("")) {
                    imageUrl = Constants.MUSICS[viewHolder.getLayoutPosition() % Constants.MUSICS.length];
                }

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_song_placeholder).into(viewHolder.ivMusic);
                viewHolder.tvName.setText(songBean.getTitle());
                viewHolder.tvInfo.setText(activity.getString(R.string.music_song_info, MusicUtils.getArtist(songBean.getArtist()), MusicUtils.getAlbum(songBean.getAlbum())));
                viewHolder.ivMore.setOnClickListener(v -> {
                    // 可以使用DialogFragment展示(下面将Activity设置为Dialog模式)
                    FragmentManager manager = activity.getSupportFragmentManager();
                    MusicMoreFragment fragment = (MusicMoreFragment) manager.findFragmentByTag(TAG_SONG);
                    if (fragment == null) {
                        fragment = MusicMoreFragment.newInstance(songBean);
                        fragment.show(manager, TAG_SONG);
                    }
                });
                viewHolder.llItem.setOnClickListener(v -> {

                });
                break;
            case MusicListFragment.TYPE_ARTIST:// 艺术家
                MusicArtistBean artistBean = musicArtistBeanList.get(i);
                name = MusicUtils.getArtist(artistBean.getArtist());
                imageUrl = Constants.MUSICS[viewHolder.getLayoutPosition() % Constants.MUSICS.length];

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_artist_placeholder).into(viewHolder.ivMusic);
                viewHolder.tvName.setText(MusicUtils.getArtist(artistBean.getArtist()));
                viewHolder.tvInfo.setText(activity.getString(R.string.music_artist_info, artistBean.getNumberOfAlbums(), artistBean.getNumberOfTracks()));
                viewHolder.ivMore.setOnClickListener(v -> MusicMoreActivity.start(activity, artistBean));
                viewHolder.llItem.setTag(imageUrl);
                viewHolder.llItem.setOnClickListener(v -> {
                    Pair<View, String> ivMusicPair = new Pair<>(viewHolder.ivMusic, activity.getString(R.string.transition_music_img));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, ivMusicPair);
                    MusicListActivity.start(activity, name, (String) viewHolder.llItem.getTag(), options.toBundle(), artistBean.get_id(), 0, null);
                });
                break;
            case MusicListFragment.TYPE_ALBUM:// 专辑
                MusicAlbumBean albumBean = musicAlbumBeanList.get(i);
                name = MusicUtils.getAlbum(albumBean.getAlbum());
                imageUrl = albumBean.getAlbumArt();
                if (imageUrl == null || imageUrl.equals("")) {
                    imageUrl = Constants.MUSICS[viewHolder.getLayoutPosition() % Constants.MUSICS.length];
                }

                Glide.with(activity).load(imageUrl).placeholder(R.drawable.ic_album_placeholder).into(viewHolder.ivMusic);
                viewHolder.tvName.setText(name);
                viewHolder.tvInfo.setText(activity.getString(R.string.music_album_info, MusicUtils.getArtist(albumBean.getArtist()), albumBean.getNumberOfSongs()));
                viewHolder.ivMore.setOnClickListener(v -> MusicMoreActivity.start(activity, albumBean));
                viewHolder.llItem.setTag(imageUrl);
                viewHolder.llItem.setOnClickListener(v -> {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(viewHolder.ivMusic, activity.getString(R.string.transition_music_img)));
                    MusicListActivity.start(activity, name, (String) viewHolder.llItem.getTag(), options.toBundle(), 0, albumBean.get_id(), null);
                });
                break;
            case MusicListFragment.TYPE_FOLDER:// 文件夹
                String folderPath = folderList.get(i);
                name = MusicUtils.getFolderName(folderPath);

                viewHolder.ivMusic.setImageResource(R.drawable.ic_folder_placeholder);
                viewHolder.tvName.setText(MusicUtils.getFolderName(folderPath));
                viewHolder.tvInfo.setText(activity.getString(R.string.music_folder_info, integers.get(viewHolder.getLayoutPosition()), MusicUtils.getPath(folderPath)));
                viewHolder.ivMore.setOnClickListener(v -> MusicMoreActivity.start(activity, folderPath));
                viewHolder.llItem.setOnClickListener(v -> MusicListActivity.start(activity, name, "", null, 0, 0, folderPath));
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case MusicListFragment.TYPE_SONG:
                return musicSongBeanList.size();
            case MusicListFragment.TYPE_ARTIST:
                return musicArtistBeanList.size();
            case MusicListFragment.TYPE_ALBUM:
                return musicAlbumBeanList.size();
            case MusicListFragment.TYPE_FOLDER:
                return folderList.size();
        }
        return 0;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

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