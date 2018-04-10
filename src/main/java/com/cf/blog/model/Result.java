package com.cf.blog.model;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 * 返回操作状态
 */
public enum Result {
    SUCCESS(0, "成功"),
    FAILED(-1, "失败");

    public int status;
    public String msg;
    private Result(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
    public int getStatus(){
        return this.status;
    }
    public String getMsg(){
        return this.msg;
    }
}
