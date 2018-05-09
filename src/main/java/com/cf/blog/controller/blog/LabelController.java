package com.cf.blog.controller.blog;

import com.cf.blog.model.JsonResult;
import com.cf.blog.model.PageResult;
import com.cf.blog.model.ResultStatus;
import com.cf.blog.model.blog.Article;
import com.cf.blog.model.blog.Label;
import com.cf.blog.service.blog.IArticleService;
import com.cf.blog.service.blog.ILabelService;
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

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Controller
@RequestMapping("/label")//文章模块
public class LabelController {
    @Resource(name = "labelService")
    private ILabelService labelService;

    /** 获取标签列表 */
    @RequestMapping("/list.do")
    @ResponseBody
    public PageResult<List<Label>> getLabelList(HttpServletRequest request){
//        int page = Integer.parseInt(request.getParameter("page"));
//        int limit = Integer.parseInt(request.getParameter("limit"));
//        int start = (page - 1) * limit;
        List<Label> labelList = labelService.getLabelList(0, 50);//TODO 分页查询
        return null != labelList ? new PageResult(0,"",1000, labelList) : new PageResult(0,"",1000, new ArrayList<>());
    }
}
