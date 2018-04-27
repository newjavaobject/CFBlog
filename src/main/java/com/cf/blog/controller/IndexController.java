package com.cf.blog.controller;

import com.cf.blog.model.blog.Article;
import com.cf.blog.service.blog.IArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Controller
public class IndexController {
    @Resource(name = "articleService")
    private IArticleService articleService;

    @RequestMapping("/index.html")//首页
    public String toIndex(HttpServletRequest request){
        List<Article> timeList = articleService.getArtileTitleList(0);//时间排序
        List<Article> countList = articleService.getArtileTitleList(1);//浏览量排序
        List<Article> articleList = articleService.getArticleList(null, 0, 5);
        request.setAttribute("tList", timeList);
        request.setAttribute("cList", countList);
        request.setAttribute("aList", articleList);
        return "index";
    }
}
