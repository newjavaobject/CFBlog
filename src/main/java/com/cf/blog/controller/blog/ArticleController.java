package com.cf.blog.controller.blog;

import com.cf.blog.model.JsonResult;
import com.cf.blog.model.Result;
import com.cf.blog.model.blog.Article;
import com.cf.blog.service.blog.IArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Controller
@RequestMapping("/article")//文章模块
public class ArticleController {
    @Resource(name = "articleService")
    private IArticleService articleService;

    /** 新增文章 */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult<String> addArticle(@RequestBody Article article){
        int result = articleService.insertArticle(article);
        if (result > 0) {
            return new JsonResult<>(Result.SUCCESS.getStatus(), Result.SUCCESS.getMsg(), "新增文章成功");
        } else {
            return new JsonResult<>(Result.FAILED.getStatus(), Result.FAILED.getMsg(), "新增文章失败");
        }
    }

    /** 删除文章 */
    @RequestMapping("/del/{id}")
    @ResponseBody
    public JsonResult<String> delArticle(@PathVariable("id") long id){
        articleService.deleteArticle(id);
        return new JsonResult<>(Result.SUCCESS.getStatus(), Result.SUCCESS.getMsg(), "删除文章成功");
    }

    /** 更新文章 */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public JsonResult<String> updateArticle(@PathVariable("id") Article article){
        int result = articleService.updateArticle(article);
        if (result > 0) {
            return new JsonResult<>(Result.SUCCESS.getStatus(), Result.SUCCESS.getMsg(), "编辑文章成功");
        } else {
            return new JsonResult<>(Result.FAILED.getStatus(), Result.FAILED.getMsg(), "编辑文章失败");
        }
    }

    /** 获取一篇文章 */
    @RequestMapping("/get/{id}")
//    @ResponseBody  --  返回html页面
    public String getArticle(@PathVariable long id){
        Article article = articleService.getArticleById(id);
        if (null == article) {
            article = new Article();
            article.setTitle("没有找到文章");
            article.setContent("没有找到文章");
        }

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("article", article);
        return "blog_detail";
    }

    /** 根据条件获取文章列表 */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult<List<Article>> getArticleList(@RequestBody Article article){
        List<Article> articleList = articleService.getArticleList(article);
        if (null != articleList) {
            return new JsonResult<>(Result.SUCCESS.getStatus(), Result.SUCCESS.getMsg(), articleList);
        } else {
            return new JsonResult<>(Result.FAILED.getStatus(), Result.FAILED.getMsg(), null);
        }
    }
}
