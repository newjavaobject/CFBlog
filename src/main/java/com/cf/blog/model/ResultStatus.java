package com.cf.blog.model;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 * 返回操作状态
 */
public class ResultStatus {
    public static final int SUCCESS = 0;
    public static final String SUCCESS_MSG = "成功";

    public static final int FAILED = -1;
    public static final String FAILED_MSG = "失败";

    public static final String FAILED_NO_TITLE = "请输入标题";
    public static final String FAILED_NO_CONTENT = "请输入内容";
}
