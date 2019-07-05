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

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.decoration.DividerDecoration;
import com.codearms.maoqiqi.one.music.adapter.MusicAdapter;
import com.codearms.maoqiqi.one.music.presenter.MusicListPresenter;
import com.codearms.maoqiqi.one.music.presenter.contract.MusicListContract;
import com.codearms.maoqiqi.one.utils.PermissionManager;
import com.codearms.maoqiqi.one.utils.SortOrder;
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

    public static MusicListFragment newInstance(long artistId, long albumId, String folderPath) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", TYPE_SONG);
        bundle.putLong("artistId", artistId);
        bundle.putLong("albumId", albumId);
        bundle.putString("folderPath", folderPath);
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
        artistId = bundle.getLong("artistId");
        albumId = bundle.getLong("albumId");
        folderPath = bundle.getString("folderPath");

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
        MusicAdapter<MusicSongBean> adapter = new MusicAdapter<>(R.layout.item_music_list, musicSongBeanList, getActivity(), type, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setArtistList(List<MusicArtistBean> musicArtistBeanList) {
        MusicAdapter<MusicArtistBean> adapter = new MusicAdapter<>(R.layout.item_music_list, musicArtistBeanList, getActivity(), type, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAlbumList(List<MusicAlbumBean> musicAlbumBeanList) {
        MusicAdapter<MusicAlbumBean> adapter = new MusicAdapter<>(R.layout.item_music_list, musicAlbumBeanList, getActivity(), type, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setFolderList(List<String> folderList, List<Integer> integers) {
        MusicAdapter<String> adapter = new MusicAdapter<>(R.layout.item_music_list, folderList, getActivity(), type, integers);
        recyclerView.setAdapter(adapter);
    }
}