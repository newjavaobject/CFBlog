package com.cf.blog.controller.blog;

import com.cf.blog.model.JsonResult;
import com.cf.blog.model.PageResult;
import com.cf.blog.model.ResultStatus;
import com.cf.blog.model.blog.Article;
import com.cf.blog.model.blog.Category;
import com.cf.blog.service.blog.IArticleService;
import com.cf.blog.service.blog.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Controller
@RequestMapping("/category")//文章模块
public class CategoryController {
    @Resource(name = "categoryService")
    private ICategoryService categoryService;


    /** 到文章分类页面 */
    @RequestMapping("/category.html")
    public String category(){
        return "admin/blog/blog_category";
    }

    /** 新增分类 */
    @RequestMapping("/add.do")
    @ResponseBody
    public JsonResult<String> addCategory(Category category){
        if(!StringUtils.hasText(category.getName())){
            return new JsonResult<>(ResultStatus.FAILED, ResultStatus.FAILED_NO_TITLE, "新增文章分类失败");
        }

        category.setId(UUID.randomUUID().toString().replaceAll("-",""));
        int result = categoryService.insertCategory(category);
        if (result > 0) {
            return new JsonResult<>(ResultStatus.SUCCESS, result + "", "新增文章分类成功");
        } else {
            return new JsonResult<>(ResultStatus.FAILED, ResultStatus.FAILED_MSG, "新增文章分类失败");
        }
    }

    /** 删除文章分类 */
    @RequestMapping("/del/{id}.html")
    @ResponseBody
    public JsonResult<String> delCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
        return new JsonResult<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS_MSG, "删除文章分类成功");
    }

    /** 更新文章分类 */
    @RequestMapping("/update/{id}.do")
    @ResponseBody
    public JsonResult<String> updateCategory(@PathVariable("id") Category category){
        int result = categoryService.updateCategory(category);
        if (result > 0) {
            return new JsonResult<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS_MSG, "编辑文章分类成功");
        } else {
            return new JsonResult<>(ResultStatus.FAILED, ResultStatus.FAILED_MSG, "编辑文章分类失败");
        }
    }

    /** 根据条件获取文章列表 */
    @RequestMapping("/list.do")
    @ResponseBody
    public PageResult<List<Article>> getCategoryList(Category category, HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = (page - 1) * limit;
        List<Category> categoryList = categoryService.getCategoryList(null, start, limit);
        return null != categoryList ? new PageResult(0,"",1000, categoryList) : new PageResult(0,"",1000, new ArrayList<>());
    }
}
