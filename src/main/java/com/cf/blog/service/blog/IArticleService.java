package com.cf.blog.service.blog;

import com.cf.blog.model.blog.Article;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
public interface IArticleService {
    /** 根据Id获取文章 */
    Article getArticleById(long id);

    /** 根据条件查询文章列表 */
    List<Article> getArticleList(Article article, int start, int limit);

    /** 新增一篇文章 */
    int insertArticle(Article article);

    /** 更新一篇文章 */
    int updateArticle(Article article, boolean status);

    /** 根据Id删除一篇文章 */
    void deleteArticle(long id);
}
