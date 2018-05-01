package com.cf.blog.dao.blog;

import com.cf.blog.model.blog.Article;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
public interface ArticleDAO {
    /* 根据Id获取文章 */
    Article getArticleById(long id);

    /* 根据条件查询文章列表 */
    List<Article> getArticleList(Article article, int page, int limit);

    /* 新增一篇文章 */
    int insertArticle(Article article);

    /* 更新一篇文章，status为true表示只更新状态，false不修改其状态 */
    int updateArticle(Article article, boolean status);

    /* 根据Id删除一篇文章 */
    void deleteArticle(long id);

    /**
     * 0：按时间排序 1：按浏览量排序
     * @return
     */
    List<Article> getArticleTitleList(int order);

    /* 点赞 */
    int updateLike(Article article);

    /* 浏览 */
    int updateView(Article article);
}
