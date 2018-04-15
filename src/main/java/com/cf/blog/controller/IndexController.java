package com.cf.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Controller
public class IndexController {
    @RequestMapping("/index.html")//首页
    public String toIndex(HttpServletRequest request){
        return "index";
    }
}
