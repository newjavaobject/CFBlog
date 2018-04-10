package com.cf.blog.service.blog;

import com.cf.blog.dao.blog.ArticleDAO;
import com.cf.blog.model.blog.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Repository("articleService")
public class ArticleService implements IArticleService {
    @Resource(name = "articleDAO")
    protected ArticleDAO articleDAO;

    @Override
    public Article getArticleById(long id) {
        return articleDAO.getArticleById(id);
    }

    @Override
    public List<Article> getArticleList(Article article) {
        return articleDAO.getArticleList(article);
    }

    @Override
    public int insertArticle(Article article) {
        return articleDAO.insertArticle(article);
    }

    @Override
    public int updateArticle(Article article) {
        return 0;
    }

    @Override
    public void deleteArticle(long id) {

    }

    private class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            Article article = new Article();
            article.setId(resultSet.getLong("ID"));
            return article;
        }
    }
}
