package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsBean {

    private String date;
    @SerializedName("stories")
    private List<NewsItemBean> newsItemBeans;
    @SerializedName("top_stories")
    private List<NewsItemBean> topNewsItemBeans;

    public class NewsItemBean {
        private int id;
        private int type;
        @SerializedName("ga_prefix")
        private String gaPrefix;
        private String title;
        private List<String> images;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGaPrefix() {
            return gaPrefix;
        }

        public void setGaPrefix(String gaPrefix) {
            this.gaPrefix = gaPrefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsItemBean> getNewsItemBeans() {
        return newsItemBeans;
    }

    public void setNewsItemBeans(List<NewsItemBean> newsItemBeans) {
        this.newsItemBeans = newsItemBeans;
    }

    public List<NewsItemBean> getTopNewsItemBeans() {
        return topNewsItemBeans;
    }

    public void setTopNewsItemBeans(List<NewsItemBean> topNewsItemBeans) {
        this.topNewsItemBeans = topNewsItemBeans;
    }
}