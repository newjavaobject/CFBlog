package com.cf.blog.controller;

import com.cf.blog.service.blog.IArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Controller
public class IndexController {
    @Resource(name = "articleService")
    private IArticleService articleService;

    @RequestMapping("/index.html")//首页
    public String toIndex(HttpServletRequest request){
//        articleService.updateArticle();
        return "index";
    }
}
