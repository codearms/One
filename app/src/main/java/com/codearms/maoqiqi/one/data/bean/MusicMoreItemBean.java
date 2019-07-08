package com.codearms.maoqiqi.one.data.bean;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-05 14:18
 */
public class MusicMoreItemBean {

    // 信息标题
    private String title;

    // 图片ID
    private int imageId;

    public MusicMoreItemBean(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}