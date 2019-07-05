package com.codearms.maoqiqi.one.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaLoader {

    private static final String TAG = MediaLoader.class.getSimpleName();
    private static final int FILTER_SIZE = 1024 * 1024;// 1MB
    private static final int FILTER_DURATION = 60 * 1000;// 1分钟

    private static List<MusicSongBean> getSongsForCursor(Cursor cursor) {
        List<MusicSongBean> list = new ArrayList<>();
        try {
            if ((cursor != null) && (cursor.moveToFirst())) {
                // cursor.getColumnIndex:得到指定列名的索引号,就是说这个字段是第几列
                // cursor.getString(columnIndex) 可以得到当前行的第几列的值
                int _id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int displayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int size = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int artistId = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

                do {
                    MusicSongBean bean = new MusicSongBean();

                    bean.set_id(cursor.getLong(_id));
                    bean.setTitle(cursor.getString(title));
                    bean.setDisplayName(cursor.getString(displayName));
                    bean.setData(cursor.getString(data));
                    bean.setDuration(cursor.getLong(duration));
                    bean.setSize(cursor.getLong(size));
                    bean.setAlbumId(cursor.getLong(albumId));
                    bean.setAlbum(cursor.getString(album));
                    bean.setArtistId(cursor.getLong(artistId));
                    bean.setArtist(cursor.getString(artist));

                    list.add(bean);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    private static List<MusicArtistBean> getArtistsForCursor(Cursor cursor) {
        List<MusicArtistBean> list = new ArrayList<>();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int _id = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                int numberOfAlbums = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
                int numberOfTracks = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

                do {
                    MusicArtistBean bean = new MusicArtistBean();

                    bean.set_id(cursor.getLong(_id));
                    bean.setArtist(cursor.getString(artist));
                    bean.setNumberOfAlbums(cursor.getLong(numberOfAlbums));
                    bean.setNumberOfTracks(cursor.getLong(numberOfTracks));

                    list.add(bean);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    private static List<MusicAlbumBean> getAlbumsForCursor(Cursor cursor) {
        List<MusicAlbumBean> list = new ArrayList<>();
        try {
            if ((cursor != null) && (cursor.moveToFirst())) {
                int _id = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                int album = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                int numberOfSongs = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
                int minYear = cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR);
                int albumArt = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                do {
                    MusicAlbumBean bean = new MusicAlbumBean();

                    bean.set_id(cursor.getLong(_id));
                    bean.setAlbum(cursor.getString(album));
                    bean.setArtist(cursor.getString(artist));
                    bean.setNumberOfSongs(cursor.getLong(numberOfSongs));
                    bean.setMinYear(cursor.getLong(minYear));
                    bean.setAlbumArt(cursor.getString(albumArt));

                    list.add(bean);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    private static List<String> getFoldersForCursor(Cursor cursor) {
        List<String> list = new ArrayList<>();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int data = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);

                String filePath;
                do {
                    filePath = cursor.getString(data);
                    list.add(filePath.substring(0, filePath.lastIndexOf(File.separator)));
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    // 得到 Cursor
    private static Cursor makeCursor(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        ContentResolver resolver = App.getInstance().getContentResolver();
        return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    // 得到歌曲列表 Cursor
    private static Cursor getSongCursor(long artistId, long albumId, String sortOrder) {
        // Uri 地址
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 查询字段
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST
        };
        // 帅选条件(MediaStore.Audio.Media.IS_MUSIC=1 and MediaStore.Audio.Media.TITLE!='')
        StringBuilder builder = new StringBuilder(MediaStore.Audio.Media.IS_MUSIC + " = 1 " + " AND " + MediaStore.Audio.Media.TITLE + " != ''");
        if (artistId != 0) {
            builder.append(" AND ").append(MediaStore.Audio.Media.ARTIST_ID).append(" = ").append(artistId);
        }
        if (albumId != 0) {
            builder.append(" AND ").append(MediaStore.Audio.Media.ALBUM_ID).append(" = ").append(albumId);
        }
        String selection = builder.toString();
        // 帅选条件参数
        String[] selectionArgs = null;
        // 排序
        // String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        if (sortOrder == null) {
            sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        }

        return makeCursor(uri, projection, selection, selectionArgs, sortOrder);
    }

    // 得到歌曲列表
    public static List<MusicSongBean> getSongBeanList(long artistId, long albumId, String folderPath, String sortOrder) {
        LogUtils.e(TAG, "artistId:" + artistId + ",albumId:" + albumId + ",folderPath:" + folderPath);
        List<MusicSongBean> songBeans = getSongsForCursor(getSongCursor(artistId, albumId, sortOrder));
        if (folderPath == null || folderPath.equals("")) {
            return songBeans;
        } else {
            List<MusicSongBean> list = new ArrayList<>();

            String data;
            for (MusicSongBean bean : songBeans) {
                data = bean.getData();
                if (data.substring(0, data.lastIndexOf(File.separator)).equals(folderPath)) {
                    list.add(bean);
                }
            }
            return list;
        }
    }

    // 得到艺术家列表 Cursor
    private static Cursor getArtistCursor(String sortOrder) {
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };
        String selection = null;
        String[] selectionArgs = null;
        // String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        if (sortOrder == null) {
            sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        }

        return makeCursor(uri, projection, selection, selectionArgs, sortOrder);
    }

    // 得到艺术家列表
    public static List<MusicArtistBean> getArtistBeanList(String sortOrder) {
        return getArtistsForCursor(getArtistCursor(sortOrder));
    }

    // 得到专辑列表 Cursor
    private static Cursor getAlbumCursor(String sortOrder) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.FIRST_YEAR,
                MediaStore.Audio.Albums.ALBUM_ART
        };
        String selection = null;
        String[] selectionArgs = null;
        // String sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        if (sortOrder == null) {
            sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        }

        return makeCursor(uri, projection, selection, selectionArgs, sortOrder);
    }

    // 得到专辑列表
    public static List<MusicAlbumBean> getAlbumBeanList(String sortOrder) {
        return getAlbumsForCursor(getAlbumCursor(sortOrder));
    }

    // 获取包含音频文件的文件夹信息 Cursor
    private static Cursor getFolderCursor() {
        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection = {MediaStore.Files.FileColumns.DATA};

        // 查询语句：检索出.mp3为后缀名,时长大于1分钟,文件大小大于1MB的媒体文件

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
                + " and " + "(" + MediaStore.Files.FileColumns.DATA + " like '%.mp3' or " + MediaStore.Audio.Media.DATA + " like'%.wma')"
                + " and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE
                + " and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION
                + ") group by ( " + MediaStore.Files.FileColumns.PARENT;
        String[] selectionArgs = null;
        String sortOrder = null;

        return makeCursor(uri, projection, selection, selectionArgs, sortOrder);
    }

    // 获取包含音频文件的文件夹信息
    public static List<String> getFolderList(Context context) {
        return getFoldersForCursor(getFolderCursor());
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    // 通过album_id,找到其所对应的专辑图片路径
    public static String getAlbumArt(long albumId) {
        String albumArt = "";

        Uri uri = Uri.parse(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI + "/" + albumId);
        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;

        try (Cursor cursor = makeCursor(uri, projection, selection, selectionArgs, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToNext();
                albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumArt;
    }
}