package com.cf.blog.controller;

import com.cf.blog.model.blog.Article;
import com.cf.blog.model.blog.Label;
import com.cf.blog.service.blog.IArticleService;
import com.cf.blog.service.blog.ILabelService;
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
    @Resource(name = "labelService")
    private ILabelService labelService;

    @RequestMapping("/index.html")//首页
    public String toIndex(HttpServletRequest request){
        List<Article> timeList = articleService.getArtileTitleList(0);//时间排序
        List<Article> countList = articleService.getArtileTitleList(1);//浏览量排序
        List<Article> likeList = articleService.getArtileTitleList(2);//点赞数排序 --图文推荐处
        List<Article> articleList = articleService.getArticleList(null, 0, 5);
        List<Label> labelList = labelService.getLabelList(0, 50);//TODO 分页查询

        request.setAttribute("tList", timeList);
        request.setAttribute("cList", countList);
        request.setAttribute("lList", likeList);
        request.setAttribute("aList", articleList);
        request.setAttribute("labelList", labelList);

        return "index";
    }
}
