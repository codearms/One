package com.codearms.maoqiqi.one.home.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChildClassifyBean implements Parcelable {

    private int id;
    private int courseId;
    private int parentChapterId;
    private String name;
    private int order;
    private boolean userControlSetTop;
    private int visible;
    @SerializedName("children")
    private List<String> children;

    private ChildClassifyBean(Parcel in) {
        id = in.readInt();
        courseId = in.readInt();
        parentChapterId = in.readInt();
        name = in.readString();
        order = in.readInt();
        userControlSetTop = in.readByte() != 0;
        visible = in.readInt();
        children = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(courseId);
        dest.writeInt(parentChapterId);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeByte((byte) (userControlSetTop ? 1 : 0));
        dest.writeInt(visible);
        dest.writeStringList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChildClassifyBean> CREATOR = new Creator<ChildClassifyBean>() {
        @Override
        public ChildClassifyBean createFromParcel(Parcel in) {
            return new ChildClassifyBean(in);
        }

        @Override
        public ChildClassifyBean[] newArray(int size) {
            return new ChildClassifyBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ChildClassifyBean{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", parentChapterId=" + parentChapterId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                ", children=" + children +
                '}';
    }
}