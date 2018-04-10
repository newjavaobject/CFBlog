package com.cf.blog.model.blog;

import java.util.Date;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 * 一篇博客文章的附件
 */
public class ArticleAttachment {
    private Article article;//所属文章
    private String name;//附件名称
    private String url;//附件路径
    private Date endTime;//过期时间
    private int status;//附件状态 0：不可以  1：可用
    private int downloadCount;//下载次数

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
}
