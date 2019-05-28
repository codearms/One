package com.codearms.maoqiqi.one.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleBeans {

    private int curPage;
    private int offset;
    private int size;
    private int pageCount;
    private boolean over;
    private int total;
    @SerializedName("datas")
    private List<ArticleBean> articleBeanList;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getArticleBeanList() {
        return articleBeanList;
    }

    public void setArticleBeanList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }
}