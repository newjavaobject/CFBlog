package com.cf.blog.model.blog;

import com.cf.blog.model.user.User;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/5/9 0007.
 * 一个标签
 */
public class Label {
    private long id;
    private String label;
    private long count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
