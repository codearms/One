package com.codearms.maoqiqi.one.music.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-03 14:58
 */
public interface MusicListContract {

    interface Presenter extends BasePresenter<View> {

        /**
         * 得到歌曲列表
         */
        void getSongList(Long artistId, long albumId, String folderPath, String sortOrder);

        /**
         * 得到艺术家列表
         */
        void getArtistList(String sortOrder);

        /**
         * 得到专辑列表
         */
        void getAlbumList(String sortOrder);

        /**
         * 获取包含音频文件的文件夹信息
         */
        void getFolderList();
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示歌曲数据
         */
        void setSongList(List<MusicSongBean> musicSongBeanList);

        /**
         * 显示艺术家数据
         */
        void setArtistList(List<MusicArtistBean> musicArtistBeanList);

        /**
         * 显示专辑数据
         */
        void setAlbumList(List<MusicAlbumBean> musicAlbumBeanList);

        /**
         * 显示文件夹数据
         */
        void setFolderList(List<String> folderList, List<Integer> integers);
    }
}