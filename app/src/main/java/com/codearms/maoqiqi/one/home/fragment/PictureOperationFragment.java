package com.codearms.maoqiqi.one.home.fragment;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseBottomSheetDialogFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.decoration.DividerDecoration;
import com.codearms.maoqiqi.one.home.presenter.PictureOperationPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.PictureOperationContract;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-26 11:30
 */
public class PictureOperationFragment extends BaseBottomSheetDialogFragment<PictureOperationContract.Presenter> implements PictureOperationContract.View {

    private String url;
    private Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PictureOperationPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_operation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.save));
        list.add(getString(R.string.share));
        list.add(getString(R.string.collection));
        list.add(getString(R.string.wallpaper));
        list.add(getString(R.string.cancel));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(R.layout.item_picture_operation, list);
        recyclerViewAdapter.setOnItemClickListener((adapter, v, position) -> {
            switch (position) {
                case 0:
                    if (bitmap == null) {
                        ToastUtils.show("无法下载");
                    } else {
                        ToastUtils.show("保存图片");
                        presenter.save(url, bitmap);
                    }
                    break;
                case 1:
                    ToastUtils.show("share");
                    break;
                case 2:
                    break;
                case 3:
                    setWallpaper();
                    break;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerDecoration());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void saveSuccess(File file) {
        notifyGalleryUpdate(context, file);
        ToastUtils.show(getString(R.string.save_folder, file.getAbsolutePath()));
    }

    private void notifyGalleryUpdate(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
    }

    private void setWallpaper() {
        // 正在设置壁纸...
        new Thread(() -> {
            try {
                WallpaperManager manager = WallpaperManager.getInstance(context);
                manager.setBitmap(bitmap);
                // 设置成功
            } catch (IOException e) {
                e.printStackTrace();
                // 设置失败
            }
        }).start();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private final class RecyclerViewAdapter extends BaseQuickAdapter<String, ViewHolder> {

        RecyclerViewAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, String item) {
            helper.tvInfo.setText(item);
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        TextView tvInfo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}