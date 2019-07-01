package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailBean {
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
    // 发布时间
    @SerializedName("pubdate")
    private String pubDate;
    // 大陆发布日期
    @SerializedName("mainland_pubdate")
    private String mainLandPubDate;
    // 发布时间
    @SerializedName("pubdates")
    private List<String> pubDates;
    // 国家
    private List<String> countries;
    // 时长
    private List<String> durations;
    // 介绍
    private String summary;
    // 标签
    private List<String> tags;
    // 作者
    @SerializedName("writers")
    private List<PersonBean> writerPersonBeanList;
    // 评分数
    @SerializedName("ratings_count")
    private int ratingsCount;
    // 评分信息
    @SerializedName("rating")
    private RatingBean ratingBean;

    // 类型
    private String subtype;
    // 详情地址
    private String alt;
    // 手机详情地址
    @SerializedName("mobile_url")
    private String mobileUrl;
    // 分享地址
    @SerializedName("share_url")
    private String shareUrl;

    // 想看
    @SerializedName("wish_count")
    private int wishCount;
    // 收藏
    private String collection;
    // 收藏
    @SerializedName("collect_count")
    private int collectCount;
    // 网站
    private String website;
    // 豆瓣网址
    @SerializedName("douban_site")
    private String douBanSite;

    // 主演
    @SerializedName("casts")
    private List<PersonBean> castPersonBeanList;
    // 导演
    @SerializedName("directors")
    private List<PersonBean> directorsPersonBeanList;

    // 照片
    @SerializedName("photos_count")
    private int photosCount;
    // 照片
    @SerializedName("photos")
    private List<PhotoBean> photoBeanList;
    // 预告片
    @SerializedName("trailers")
    private List<VideoBean> trailerVideoBeanList;
    // 预告片地址
    @SerializedName("trailer_urls")
    private List<String> trailerUrls;
    // 花絮
    @SerializedName("bloopers")
    private List<VideoBean> bloopersVideoBeanList;
    // 花絮地址
    @SerializedName("blooper_urls")
    private List<String> blooperUrls;

    // 评论数
    @SerializedName("comments_count")
    private int commentsCount;
    // 评论数
    @SerializedName("reviews_count")
    private int reviewsCount;
    // 评论
    @SerializedName("popular_reviews")
    private List<PopularReviewsBean> popularReviews;
    // 受欢迎的评论
    @SerializedName("popular_comments")
    private List<PopularCommentsBean> popularCommentsBeanList;

    // 是否有票
    @SerializedName("has_ticket")
    private boolean hasTicket;
    @SerializedName("do_count")
    private Object doCount;
    @SerializedName("current_season")
    private Object currentSeason;
    @SerializedName("seasons_count")
    private Object seasonsCount;
    @SerializedName("episodes_count")
    private Object episodesCount;
    private List<String> languages;
    // 是否有影讯
    @SerializedName("has_schedule")
    private boolean hasSchedule;
    // 影讯
    @SerializedName("schedule_url")
    private String scheduleUrl;

    // @SerializedName("has_video")
    // private boolean hasVideo;
    // private List<String> videos;
    // @SerializedName("clip_urls")
    // private Lis<String> clipUrls;
    // private List<String> clips;
    // private List<String> aka;

    public static class ImageBean {
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public static class PersonBean {
        private String id;
        // 存放各种大小的作者图
        @SerializedName("avatars")
        private ImageBean imageBean;
        private String name;
        @SerializedName("name_en")
        private String nameEn;
        private String alt;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }
    }

    public static class RatingBean {
        private int min;
        private int max;
        private double average;
        private String stars;
        @SerializedName("details")
        private DetailBean detailBean;

        public static class DetailBean {
            @SerializedName("1")
            private int $1;
            @SerializedName("2")
            private int $2;
            @SerializedName("3")
            private int $3;
            @SerializedName("4")
            private int $4;
            @SerializedName("5")
            private int $5;

            public int get$1() {
                return $1;
            }

            public void set$1(int $1) {
                this.$1 = $1;
            }

            public int get$2() {
                return $2;
            }

            public void set$2(int $2) {
                this.$2 = $2;
            }

            public int get$3() {
                return $3;
            }

            public void set$3(int $3) {
                this.$3 = $3;
            }

            public int get$4() {
                return $4;
            }

            public void set$4(int $4) {
                this.$4 = $4;
            }

            public int get$5() {
                return $5;
            }

            public void set$5(int $5) {
                this.$5 = $5;
            }
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public DetailBean getDetailBean() {
            return detailBean;
        }

        public void setDetailBean(DetailBean detailBean) {
            this.detailBean = detailBean;
        }
    }

    public static class VideoBean {
        private String id;
        @SerializedName("subject_id")
        private String subjectId;
        private String title;
        private String small;
        private String medium;
        private String alt;
        @SerializedName("resource_url")
        private String resourceUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }
    }

    public static class PhotoBean {
        private String id;
        private String icon;
        private String thumb;
        private String image;
        private String cover;
        private String alt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }
    }

    public static class PopularCommentsBean {
        private String id;
        @SerializedName("subject_id")
        private String subjectId;
        private String content;
        @SerializedName("author")
        private AuthorBean authorBean;
        @SerializedName("useful_count")
        private int usefulCount;
        @SerializedName("rating")
        private CommentsRatingBean commentsRatingBean;
        @SerializedName("created_at")
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public AuthorBean getAuthorBean() {
            return authorBean;
        }

        public void setAuthorBean(AuthorBean authorBean) {
            this.authorBean = authorBean;
        }

        public int getUsefulCount() {
            return usefulCount;
        }

        public void setUsefulCount(int usefulCount) {
            this.usefulCount = usefulCount;
        }

        public CommentsRatingBean getCommentsRatingBean() {
            return commentsRatingBean;
        }

        public void setCommentsRatingBean(CommentsRatingBean commentsRatingBean) {
            this.commentsRatingBean = commentsRatingBean;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    public static class PopularReviewsBean {
        private String id;
        private String title;
        @SerializedName("subject_id")
        private String subjectId;
        @SerializedName("author")
        private AuthorBean authorBean;
        private String summary;
        @SerializedName("rating")
        private CommentsRatingBean commentsRatingBean;
        private String alt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public AuthorBean getAuthorBean() {
            return authorBean;
        }

        public void setAuthorBean(AuthorBean authorBean) {
            this.authorBean = authorBean;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public CommentsRatingBean getCommentsRatingBean() {
            return commentsRatingBean;
        }

        public void setCommentsRatingBean(CommentsRatingBean commentsRatingBean) {
            this.commentsRatingBean = commentsRatingBean;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }
    }

    public static class CommentsRatingBean {
        private int min;
        private int max;
        private int value;

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class AuthorBean {
        private String id;
        private String uid;
        private String name;
        private String avatar;
        private String alt;
        private String signature;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

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

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
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

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getDurations() {
        return durations;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<PersonBean> getWriterPersonBeanList() {
        return writerPersonBeanList;
    }

    public void setWriterPersonBeanList(List<PersonBean> writerPersonBeanList) {
        this.writerPersonBeanList = writerPersonBeanList;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public RatingBean getRatingBean() {
        return ratingBean;
    }

    public void setRatingBean(RatingBean ratingBean) {
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

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getWishCount() {
        return wishCount;
    }

    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDouBanSite() {
        return douBanSite;
    }

    public void setDouBanSite(String douBanSite) {
        this.douBanSite = douBanSite;
    }

    public List<PersonBean> getCastPersonBeanList() {
        return castPersonBeanList;
    }

    public void setCastPersonBeanList(List<PersonBean> castPersonBeanList) {
        this.castPersonBeanList = castPersonBeanList;
    }

    public List<PersonBean> getDirectorsPersonBeanList() {
        return directorsPersonBeanList;
    }

    public void setDirectorsPersonBeanList(List<PersonBean> directorsPersonBeanList) {
        this.directorsPersonBeanList = directorsPersonBeanList;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }

    public List<PhotoBean> getPhotoBeanList() {
        return photoBeanList;
    }

    public void setPhotoBeanList(List<PhotoBean> photoBeanList) {
        this.photoBeanList = photoBeanList;
    }

    public List<VideoBean> getTrailerVideoBeanList() {
        return trailerVideoBeanList;
    }

    public void setTrailerVideoBeanList(List<VideoBean> trailerVideoBeanList) {
        this.trailerVideoBeanList = trailerVideoBeanList;
    }

    public List<String> getTrailerUrls() {
        return trailerUrls;
    }

    public void setTrailerUrls(List<String> trailerUrls) {
        this.trailerUrls = trailerUrls;
    }

    public List<VideoBean> getBloopersVideoBeanList() {
        return bloopersVideoBeanList;
    }

    public void setBloopersVideoBeanList(List<VideoBean> bloopersVideoBeanList) {
        this.bloopersVideoBeanList = bloopersVideoBeanList;
    }

    public List<String> getBlooperUrls() {
        return blooperUrls;
    }

    public void setBlooperUrls(List<String> blooperUrls) {
        this.blooperUrls = blooperUrls;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public List<PopularReviewsBean> getPopularReviews() {
        return popularReviews;
    }

    public void setPopularReviews(List<PopularReviewsBean> popularReviews) {
        this.popularReviews = popularReviews;
    }

    public List<PopularCommentsBean> getPopularCommentsBeanList() {
        return popularCommentsBeanList;
    }

    public void setPopularCommentsBeanList(List<PopularCommentsBean> popularCommentsBeanList) {
        this.popularCommentsBeanList = popularCommentsBeanList;
    }

    public boolean isHasTicket() {
        return hasTicket;
    }

    public void setHasTicket(boolean hasTicket) {
        this.hasTicket = hasTicket;
    }

    public Object getDoCount() {
        return doCount;
    }

    public void setDoCount(Object doCount) {
        this.doCount = doCount;
    }

    public Object getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Object currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Object getSeasonsCount() {
        return seasonsCount;
    }

    public void setSeasonsCount(Object seasonsCount) {
        this.seasonsCount = seasonsCount;
    }

    public Object getEpisodesCount() {
        return episodesCount;
    }

    public void setEpisodesCount(Object episodesCount) {
        this.episodesCount = episodesCount;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public boolean isHasSchedule() {
        return hasSchedule;
    }

    public void setHasSchedule(boolean hasSchedule) {
        this.hasSchedule = hasSchedule;
    }

    public String getScheduleUrl() {
        return scheduleUrl;
    }

    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }
}