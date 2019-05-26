package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NavigationBean {

    private int cid;
    private String name;
    @SerializedName("articles")
    private List<ArticleBeans.ItemArticleBean> list;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleBeans.ItemArticleBean> getList() {
        return list;
    }

    public void setList(List<ArticleBeans.ItemArticleBean> list) {
        this.list = list;
    }
}