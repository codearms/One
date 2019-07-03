package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListBean {
    private int count;
    private int start;
    private int total;
    private String title;
    @SerializedName("subjects")
    private List<MovieBean> movieBeanList;

    public class MovieBean {
        private String id;
        // 存放各种大小的电影图
        @SerializedName("images")
        private ImageBean imageBean;
        // 电影中文名
        private String title;
        // 电影原名
        @SerializedName("original_title")
        private String originalTitle;
        // 电影类型
        private List<String> genres;
        // 年份
        private String year;
        // 大陆发布日期
        @SerializedName("mainland_pubdate")
        private String mainLandPubDate;
        // 发布时间
        @SerializedName("pubdates")
        private List<String> pubDates;
        // 时长
        private List<String> durations;
        // 评分信息
        @SerializedName("rating")
        private MovieDetailBean.RatingBean ratingBean;
        // 类型
        private String subtype;
        // 详情地址
        private String alt;
        // 收藏
        @SerializedName("collect_count")
        private int collectCount;
        // 主演
        @SerializedName("casts")
        private List<MovieDetailBean.PersonBean> castPersonBeanList;
        // 导演
        @SerializedName("directors")
        private List<MovieDetailBean.PersonBean> directorsPersonBeanList;

        // @SerializedName("has_video")
        // private boolean hasVideo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ImageBean getImageBean() {
            return imageBean;
        }

        public void setImageBean(ImageBean imageBean) {
            this.imageBean = imageBean;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMainLandPubDate() {
            return mainLandPubDate;
        }

        public void setMainLandPubDate(String mainLandPubDate) {
            this.mainLandPubDate = mainLandPubDate;
        }

        public List<String> getPubDates() {
            return pubDates;
        }

        public void setPubDates(List<String> pubDates) {
            this.pubDates = pubDates;
        }

        public List<String> getDurations() {
            return durations;
        }

        public void setDurations(List<String> durations) {
            this.durations = durations;
        }

        public MovieDetailBean.RatingBean getRatingBean() {
            return ratingBean;
        }

        public void setRatingBean(MovieDetailBean.RatingBean ratingBean) {
            this.ratingBean = ratingBean;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public List<MovieDetailBean.PersonBean> getCastPersonBeanList() {
            return castPersonBeanList;
        }

        public void setCastPersonBeanList(List<MovieDetailBean.PersonBean> castPersonBeanList) {
            this.castPersonBeanList = castPersonBeanList;
        }

        public List<MovieDetailBean.PersonBean> getDirectorsPersonBeanList() {
            return directorsPersonBeanList;
        }

        public void setDirectorsPersonBeanList(List<MovieDetailBean.PersonBean> directorsPersonBeanList) {
            this.directorsPersonBeanList = directorsPersonBeanList;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieBean> getMovieBeanList() {
        return movieBeanList;
    }

    public void setMovieBeanList(List<MovieBean> movieBeanList) {
        this.movieBeanList = movieBeanList;
    }
}