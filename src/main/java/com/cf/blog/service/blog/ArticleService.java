package com.cf.blog.service.blog;

import com.cf.blog.dao.blog.ArticleDAO;
import com.cf.blog.model.blog.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Service("articleService")
public class ArticleService implements IArticleService {
    @Resource(name = "articleDAO")
    protected ArticleDAO articleDAO;

    @Override
    public Article getArticleById(long id) {
        return articleDAO.getArticleById(id);
    }

    @Override
    public List<Article> getArticleList(Article article, int page, int limit) {
        return articleDAO.getArticleList(article, page, limit);
    }

    @Override
    public int insertArticle(Article article) {
        return articleDAO.insertArticle(article);
    }

    @Override
    public int updateArticle(Article article, boolean status) {
        return articleDAO.updateArticle(article, status);
    }

    @Override
    public void deleteArticle(long id) {
        articleDAO.deleteArticle(id);
    }

    @Override
    public List<Article> getArtileTitleList(int order) {
        return articleDAO.getArticleTitleList(order);
    }

    @Override
    public int updateLike(Article article) {
        return articleDAO.updateLike(article);
    }

    @Override
    public int updateView(Article article) {
        return articleDAO.updateView(article);
    }
}
