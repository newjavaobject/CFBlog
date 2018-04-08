package com.cf.blog.pojo.blog;

import com.cf.blog.pojo.User;

import java.util.Date;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * 一篇博客文章
 */
public class Blog {
    private long id;
    private String title;
    private Date createTime;//创建时间
    private Date startTime;//生效时间
    private Date endTime;//失效时间
    private int status;//状态 0：编辑中  1：可发布   2：失效，不能发布
    private String label;//标签
    private String content;//文章正文
    private long viewCount;//文章浏览量
    private long likeCount;//点赞数量

    private User user;//创建人

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
}
