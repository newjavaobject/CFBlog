package com.cf.blog.design.decorate;

/**
 * 饮料超类
 */
public class Beberage {
    public String describtion;//描述

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    /**
     * 计算饮料价格
     * @return
     */
    public float cost(){
        return 0.01f;
    }
}
