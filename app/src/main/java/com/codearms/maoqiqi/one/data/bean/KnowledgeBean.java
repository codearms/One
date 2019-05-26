package com.codearms.maoqiqi.one.data.bean;

import java.util.List;

public class KnowledgeBean {

    private int id;
    private int courseId;
    private int parentChapterId;
    private String name;
    private int order;
    private boolean userControlSetTop;
    private int visible;
    private List<WeChatBean> children;

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

    public List<WeChatBean> getChildren() {
        return children;
    }

    public void setChildren(List<WeChatBean> children) {
        this.children = children;
    }
}