package com.codearms.maoqiqi.one.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ArticleBean implements Parcelable {

    private int id;
    // 收藏文章列表独有
    private int originId;
    private int type;
    private String title;
    private int userId;
    private String author;
    private int chapterId;
    private String chapterName;
    private int superChapterId;
    private String superChapterName;
    private String link;
    private String apkLink;
    private String projectLink;
    private List<TagBean> tags;
    private int zan;
    private int visible;
    private String desc;
    private String envelopePic;
    private long publishTime;
    private String niceDate;
    private String origin;
    private String prefix;
    private boolean fresh;
    private boolean collect;
    private int courseId;

    public static class TagBean implements Parcelable {
        private String name;
        private String url;

        private TagBean(Parcel in) {
            name = in.readString();
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<TagBean> CREATOR = new Creator<TagBean>() {
            @Override
            public TagBean createFromParcel(Parcel in) {
                return new TagBean(in);
            }

            @Override
            public TagBean[] newArray(int size) {
                return new TagBean[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "TagBean{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    protected ArticleBean(Parcel in) {
        id = in.readInt();
        originId = in.readInt();
        type = in.readInt();
        title = in.readString();
        userId = in.readInt();
        author = in.readString();
        chapterId = in.readInt();
        chapterName = in.readString();
        superChapterId = in.readInt();
        superChapterName = in.readString();
        link = in.readString();
        apkLink = in.readString();
        projectLink = in.readString();
        tags = in.createTypedArrayList(TagBean.CREATOR);
        zan = in.readInt();
        visible = in.readInt();
        desc = in.readString();
        envelopePic = in.readString();
        publishTime = in.readLong();
        niceDate = in.readString();
        origin = in.readString();
        prefix = in.readString();
        fresh = in.readByte() != 0;
        collect = in.readByte() != 0;
        courseId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(originId);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeInt(userId);
        dest.writeString(author);
        dest.writeInt(chapterId);
        dest.writeString(chapterName);
        dest.writeInt(superChapterId);
        dest.writeString(superChapterName);
        dest.writeString(link);
        dest.writeString(apkLink);
        dest.writeString(projectLink);
        dest.writeTypedList(tags);
        dest.writeInt(zan);
        dest.writeInt(visible);
        dest.writeString(desc);
        dest.writeString(envelopePic);
        dest.writeLong(publishTime);
        dest.writeString(niceDate);
        dest.writeString(origin);
        dest.writeString(prefix);
        dest.writeByte((byte) (fresh ? 1 : 0));
        dest.writeByte((byte) (collect ? 1 : 0));
        dest.writeInt(courseId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel in) {
            return new ArticleBean(in);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public List<TagBean> getTags() {
        return tags;
    }

    public void setTags(List<TagBean> tags) {
        this.tags = tags;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "id=" + id +
                ", originId=" + originId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                ", chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", superChapterId=" + superChapterId +
                ", superChapterName='" + superChapterName + '\'' +
                ", link='" + link + '\'' +
                ", apkLink='" + apkLink + '\'' +
                ", projectLink='" + projectLink + '\'' +
                ", tags=" + tags +
                ", zan=" + zan +
                ", visible=" + visible +
                ", desc='" + desc + '\'' +
                ", envelopePic='" + envelopePic + '\'' +
                ", publishTime=" + publishTime +
                ", niceDate='" + niceDate + '\'' +
                ", origin='" + origin + '\'' +
                ", prefix='" + prefix + '\'' +
                ", fresh=" + fresh +
                ", collect=" + collect +
                ", courseId=" + courseId +
                '}';
    }
}