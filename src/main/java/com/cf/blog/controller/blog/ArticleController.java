package com.cf.blog.controller.blog;

import com.cf.blog.model.JsonResult;
import com.cf.blog.model.PageResult;
import com.cf.blog.model.ResultStatus;
import com.cf.blog.model.blog.Article;
import com.cf.blog.service.blog.IArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    /** 到新增文章页面 */
    @RequestMapping("/add.html")
    public String addArticle(){
        return "admin/blog/blog_add";
    }

    /** 新增文章 */
    @RequestMapping("/add.do")
    @ResponseBody
    public JsonResult<String> addArticle(Article article){
        int result = 0;
        if (article.getId() > 0) result = articleService.updateArticle(article, false);
        else result = articleService.insertArticle(article);
        if (result > 0) {
            return new JsonResult<>(ResultStatus.SUCCESS, result + "", "新增文章成功");
        } else {
            return new JsonResult<>(ResultStatus.FAILED, ResultStatus.FAILED_MSG, "新增文章失败");
        }
    }

    /** 删除文章 */
    @RequestMapping("/del/{id}.html")
    @ResponseBody
    public JsonResult<String> delArticle(@PathVariable("id") long id){
        articleService.deleteArticle(id);
        return new JsonResult<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS_MSG, "删除文章成功");
    }

    /** 更新文章 */
    @RequestMapping("/update/{id}.html")
    @ResponseBody
    public JsonResult<String> updateArticle(@PathVariable("id") Article article){
        int result = articleService.updateArticle(article, StringUtils.hasText(article.getContent())?false:true);
        if (result > 0) {
            return new JsonResult<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS_MSG, "编辑文章成功");
        } else {
            return new JsonResult<>(ResultStatus.FAILED, ResultStatus.FAILED_MSG, "编辑文章失败");
        }
    }

    /** 获取一篇文章 */
    @RequestMapping("/get/{id}.html")
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
    @RequestMapping("/list.do")
    @ResponseBody
    public PageResult<List<Article>> getArticleList(Article article, HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = (page - 1) * limit;
        List<Article> articleList = articleService.getArticleList(null, start, limit);
        return null != articleList ? new PageResult(0,"",1000, articleList) : new PageResult(0,"",1000, new ArrayList<>());
    }
}
