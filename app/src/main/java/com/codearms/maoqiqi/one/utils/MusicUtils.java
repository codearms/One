package com.codearms.maoqiqi.one.utils;

import java.io.File;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-03 15:51
 */
public class MusicUtils {

    public static String getArtist(String artist) {
        if ("<unknown>".equals(artist)) return "未知艺术家";
        return artist;
    }

    public static String getAlbum(String album) {
        if ("<unknown>".equals(album)) return "未知专辑";
        return album;
    }

    public static String getFolderName(String folderPath) {
        return folderPath.substring(folderPath.lastIndexOf(File.separator) + 1);
    }

    public static String getPath(String folderPath) {
        return folderPath.substring(0, folderPath.lastIndexOf(File.separator) + 1);
    }
}