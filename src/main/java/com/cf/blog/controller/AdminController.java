package com.cf.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/index.html")//首页
    public String toIndex(HttpServletRequest request){
        return "admin/index";
    }

    @RequestMapping("/blog.html")//首页
    public String toBlog(HttpServletRequest request){
        return "admin/blog_list";
    }
}
