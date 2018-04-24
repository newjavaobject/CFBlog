package com.cf.blog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhiyu on 2018/4/24 0024.
 */
public class SearchCondition {
    private int page;//第多少页
    private int limit;//每页多少条
    private List<String> fields;//查询列名
    private Map<String, Object> param;//WHERE条件参数
    private List<String> groupBy;//分组
    private List<String> orderBy;//排序

    /* 增加一条搜索条件 */
    public SearchCondition addParam(String key, Object obj) {
        if (null == this.param) param = new HashMap<>();
        param.put(key, obj);
        return this;
    }

    public Map<String, Object> getParam(){
//        StringBuilder paramStr = new StringBuilder("");
//        param.forEach((k, v) -> paramStr.append());
//        return paramStr.toString();
        return param;
    }

    /* 增加一条分组 */
    public SearchCondition addGroupBy(String groupBY) {
        if (null == this.groupBy) groupBy = new ArrayList<>();
        groupBy.add(groupBY);
        return this;
    }

    public String getGroupBy(){
        StringBuilder groupByStr = new StringBuilder("");
        groupBy.forEach((str)->groupByStr.append(str));
        return groupByStr.toString();
    }

    /* 增加一条排序 */
    public SearchCondition addOrderBy(String orderBY) {
        if (null == this.orderBy) orderBy = new ArrayList<>();
        orderBy.add(orderBY);
        return this;
    }
}
