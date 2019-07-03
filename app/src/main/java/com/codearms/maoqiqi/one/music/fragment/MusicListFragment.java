package com.codearms.maoqiqi.one.music.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.decoration.DividerDecoration;
import com.codearms.maoqiqi.one.music.presenter.MusicListPresenter;
import com.codearms.maoqiqi.one.music.presenter.contract.MusicListContract;
import com.codearms.maoqiqi.one.utils.MusicUtils;
import com.codearms.maoqiqi.one.utils.PermissionManager;
import com.codearms.maoqiqi.one.utils.SortOrder;
import com.codearms.maoqiqi.utils.LogUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-03 14:21
 */
public class MusicListFragment extends BaseFragment<MusicListContract.Presenter> implements MusicListContract.View, PermissionManager.PermissionCallBack {

    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int REQUEST_PERMISSION_SETTING = 2;

    public static final int TYPE_SONG = 0;
    public static final int TYPE_ARTIST = 1;
    public static final int TYPE_ALBUM = 2;
    public static final int TYPE_FOLDER = 3;

    private int type;

    private RecyclerView recyclerView;

    private PermissionManager permissionManager;

    private long artistId;
    private long albumId;
    private String folderPath;
    private String sortOrder;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MusicListFragment.
     */
    public static MusicListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new MusicListPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_music_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle == null) return;

        type = bundle.getInt("type");

        recyclerView = rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerDecoration());
    }

    @Override
    protected void loadData() {
        super.loadData();
        permissionManager = new PermissionManager(getActivity(), PERMISSIONS, this);
        permissionManager.checkSelfPermission(getParentFragment(), REQUEST_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionRationale() {
        showPromptDialog();
    }

    @Override
    public void onSuccess() {
        LogUtils.e("info", "onSuccess");
        getData(true);
    }

    @Override
    public void onSetting() {
        showDetailSettingDialog();
    }

    @Override
    public void onFail() {
        ToastUtils.show(getString(R.string.no_authorization));
    }

    // 显示授权读取外部存储以在您的设备上显示歌曲对话框
    private void showPromptDialog() {
        new AlertDialog.Builder(context)
                .setMessage(getString(R.string.read_external_storage))
                .setPositiveButton(R.string.ok, (dialog, i) -> {
                    dialog.dismiss();
                    // 确定,请求权限
                    if (getParentFragment() == null) {
                        requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
                    } else {
                        getParentFragment().requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                    // 取消,没有权限
                    ToastUtils.show(getString(R.string.no_authorization));
                })
                .setCancelable(false)
                .create().show();
    }

    // 显示去应用程序详情设置,授权读取外部存储以在设备上显示歌曲对话框
    private void showDetailSettingDialog() {
        new AlertDialog.Builder(context)
                .setMessage(getString(R.string.to_settings))
                .setPositiveButton(R.string.ok, (dialog, i) -> {
                    dialog.dismiss();
                    // 确定,打开该应用详情设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                })
                .setNegativeButton(R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                    // 取消,没有权限
                    ToastUtils.show(getString(R.string.no_authorization));
                })
                .setCancelable(false)
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            permissionManager.permissionsResult(permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            // 还需要对权限进行判断,遍历权限数组
            for (String permission : PERMISSIONS) {
                if (!(PermissionManager.isMarshmallow() && PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(permission))) {
                    ToastUtils.show(getString(R.string.no_authorization));
                    return;
                }
            }
            onSuccess();
        }
    }

    private void getData(boolean isFirst) {
        switch (type) {
            case TYPE_SONG:
                if (!isFirst) sortOrder = SortOrder.SongSortOrder.SONG_A_Z;
                presenter.getSongList(artistId, albumId, folderPath, sortOrder);
                break;
            case TYPE_ARTIST:
                if (!isFirst) sortOrder = SortOrder.ArtistSortOrder.ARTIST_A_Z;
                presenter.getArtistList(sortOrder);
                break;
            case TYPE_ALBUM:
                if (!isFirst) sortOrder = SortOrder.AlbumSortOrder.ALBUM_A_Z;
                presenter.getAlbumList(sortOrder);
                break;
            case TYPE_FOLDER:
                presenter.getFolderList();
                break;
        }
    }

    @Override
    public void setSongList(List<MusicSongBean> musicSongBeanList) {
        RecyclerAdapter<MusicSongBean> adapter = new RecyclerAdapter<>(R.layout.item_music_list, musicSongBeanList, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setArtistList(List<MusicArtistBean> musicArtistBeanList) {
        RecyclerAdapter<MusicArtistBean> adapter = new RecyclerAdapter<>(R.layout.item_music_list, musicArtistBeanList, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAlbumList(List<MusicAlbumBean> musicAlbumBeanList) {
        RecyclerAdapter<MusicAlbumBean> adapter = new RecyclerAdapter<>(R.layout.item_music_list, musicAlbumBeanList, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setFolderList(List<String> folderList, List<Integer> integers) {
        RecyclerAdapter<String> adapter = new RecyclerAdapter<>(R.layout.item_music_list, folderList, null);
        recyclerView.setAdapter(adapter);
    }

    final class RecyclerAdapter<T> extends BaseQuickAdapter<T, ViewHolder> {

        private final List<Integer> integers;

        RecyclerAdapter(int layoutResId, @Nullable List<T> data, List<Integer> integers) {
            super(layoutResId, data);
            this.integers = integers;
        }

        @Override
        protected void convert(ViewHolder helper, T item) {
            switch (type) {
                case TYPE_SONG:// 歌曲
                    helper.ivMusic.setVisibility(View.VISIBLE);
                    helper.ivFolder.setVisibility(View.GONE);

                    MusicSongBean songBean = (MusicSongBean) item;

                    helper.tvName.setText(songBean.getTitle());
                    helper.tvInfo.setText(getString(R.string.music_song_info, MusicUtils.getArtist(songBean.getArtist()), MusicUtils.getAlbum(songBean.getAlbum())));
                    break;
                case TYPE_ARTIST:// 艺术家
                    helper.ivMusic.setVisibility(View.VISIBLE);
                    helper.ivFolder.setVisibility(View.GONE);

                    MusicArtistBean artistBean = (MusicArtistBean) item;

                    helper.tvName.setText(MusicUtils.getArtist(artistBean.getArtist()));
                    helper.tvInfo.setText(getString(R.string.music_artist_info, artistBean.getNumberOfAlbums(), artistBean.getNumberOfTracks()));
                    break;
                case TYPE_ALBUM:// 专辑
                    helper.ivMusic.setVisibility(View.VISIBLE);
                    helper.ivFolder.setVisibility(View.GONE);

                    MusicAlbumBean albumBean = (MusicAlbumBean) item;

                    helper.tvName.setText(MusicUtils.getAlbum(albumBean.getAlbum()));
                    helper.tvInfo.setText(getString(R.string.music_album_info, MusicUtils.getArtist(albumBean.getArtist()), albumBean.getNumberOfSongs()));
                    break;
                case TYPE_FOLDER:// 文件夹
                    helper.ivMusic.setVisibility(View.GONE);
                    helper.ivFolder.setVisibility(View.VISIBLE);

                    String folderPath = (String) item;

                    helper.tvName.setText(MusicUtils.getFolderName(folderPath));
                    helper.tvInfo.setText(getString(R.string.music_folder_info, integers.get(helper.getLayoutPosition()), MusicUtils.getPath(folderPath)));
                    break;
            }
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        LinearLayout llItem;
        ImageView ivMusic;
        ImageView ivFolder;
        TextView tvName;
        TextView tvInfo;
        ImageView ivMore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            ivMusic = itemView.findViewById(R.id.ivMusic);
            ivFolder = itemView.findViewById(R.id.ivFolder);
            tvName = itemView.findViewById(R.id.tvName);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            ivMore = itemView.findViewById(R.id.ivMore);
        }
    }
}