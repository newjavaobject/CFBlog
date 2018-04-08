package com.cf.blog.pojo;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 * 角色
 */
public class Role {
    private long id;
    private String name;
    private long parentId;
    private List<Role> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<Role> getChildren() {
        return children;
    }

    public void setChildren(List<Role> children) {
        this.children = children;
    }
}
