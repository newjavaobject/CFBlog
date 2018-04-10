package com.cf.blog.model;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 * 返回json格式数据的封装
 */
public class JsonResult<T> {
    private int status;//状态
    private String msg;//返回信息
    private T obj;//返回实体数据

    public JsonResult(int status, String msg, T obj){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
