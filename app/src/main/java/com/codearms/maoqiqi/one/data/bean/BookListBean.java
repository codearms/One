package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-03 10:14
 */
public class BookListBean {

    private int count;
    private int start;
    private int total;
    @SerializedName("books")
    private List<BookBean> bookBeanList;

    public static class BookBean {
        private String id;
        // 封面
        private String image;
        @SerializedName("images")
        private ImageBean imageBean;
        // 书名
        private String title;
        // 副标题
        private String subtitle;
        @SerializedName("origin_title")
        private String originTitle;
        @SerializedName("alt_title")
        private String altTitle;
        // 作者
        private List<String> author;
        // 作者简介
        @SerializedName("author_intro")
        private String authorIntro;
        // 出版社
        private String publisher;
        // 出版时间
        @SerializedName("pubdate")
        private String pubDate;
        // 内容简介
        private String summary;
        // 价格
        private String price;
        // 标签
        @SerializedName("tags")
        private List<TagBean> tagBeanList;

        // 书籍评价
        @SerializedName("rating")
        private BookDetailBean.RatingBean ratingBean;

        // 详情链接
        private String url;
        private String alt;
        // 目录
        private String catalog;
        // 页数
        private String pages;

        private String binding;
        private String isbn10;
        private String isbn13;
        private List<String> translator;

        @SerializedName("series")
        private SeriesBean seriesBeanList;
        @SerializedName("ebook_url")
        private String ebookUrl;
        @SerializedName("ebook_price")
        private String ebookPrice;

        public static class SeriesBean {
            private String id;
            private String title;

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
        }

        public static class TagBean {
            private int count;
            private String name;
            private String title;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getOriginTitle() {
            return originTitle;
        }

        public void setOriginTitle(String originTitle) {
            this.originTitle = originTitle;
        }

        public String getAltTitle() {
            return altTitle;
        }

        public void setAltTitle(String altTitle) {
            this.altTitle = altTitle;
        }

        public List<String> getAuthor() {
            return author;
        }

        public void setAuthor(List<String> author) {
            this.author = author;
        }

        public String getAuthorIntro() {
            return authorIntro;
        }

        public void setAuthorIntro(String authorIntro) {
            this.authorIntro = authorIntro;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<TagBean> getTagBeanList() {
            return tagBeanList;
        }

        public void setTagBeanList(List<TagBean> tagBeanList) {
            this.tagBeanList = tagBeanList;
        }

        public BookDetailBean.RatingBean getRatingBean() {
            return ratingBean;
        }

        public void setRatingBean(BookDetailBean.RatingBean ratingBean) {
            this.ratingBean = ratingBean;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getIsbn10() {
            return isbn10;
        }

        public void setIsbn10(String isbn10) {
            this.isbn10 = isbn10;
        }

        public String getIsbn13() {
            return isbn13;
        }

        public void setIsbn13(String isbn13) {
            this.isbn13 = isbn13;
        }

        public List<String> getTranslator() {
            return translator;
        }

        public void setTranslator(List<String> translator) {
            this.translator = translator;
        }

        public SeriesBean getSeriesBeanList() {
            return seriesBeanList;
        }

        public void setSeriesBeanList(SeriesBean seriesBeanList) {
            this.seriesBeanList = seriesBeanList;
        }

        public String getEbookUrl() {
            return ebookUrl;
        }

        public void setEbookUrl(String ebookUrl) {
            this.ebookUrl = ebookUrl;
        }

        public String getEbookPrice() {
            return ebookPrice;
        }

        public void setEbookPrice(String ebookPrice) {
            this.ebookPrice = ebookPrice;
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

    public List<BookBean> getBookBeanList() {
        return bookBeanList;
    }

    public void setBookBeanList(List<BookBean> bookBeanList) {
        this.bookBeanList = bookBeanList;
    }
}