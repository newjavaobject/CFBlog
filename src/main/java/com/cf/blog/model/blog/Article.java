package com.cf.blog.model.blog;

import com.cf.blog.model.user.User;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * 一篇博客文章
 */
public class Article {
    private long id;
    private String title;
    private int type;//文章类型 0-技术文章  1-分享文章  2-代码文章 3-bug记录文章
    private String createTime;//创建时间
    private String startTime;//生效时间
    private String endTime;//失效时间
    private int status;//状态 0：编辑，草稿箱中  1：发布中   2：失效
    private String label;//标签
    private String content;//文章正文
    private long viewCount;//文章浏览量
    private long likeCount;//点赞数量

    private User user;//创建人
    private List<ArticleAttachment> attachments;//附件

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArticleAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ArticleAttachment> attachments) {
        this.attachments = attachments;
    }
}
