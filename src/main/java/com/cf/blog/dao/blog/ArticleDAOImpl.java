package com.cf.blog.dao.blog;

import com.cf.blog.model.blog.Article;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Repository("articleDAO")
public class ArticleDAOImpl implements ArticleDAO {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Override
    public Article getArticleById(long id) {
        String sql = "SELECT * FROM tb_article WHERE id = ?";
        List<Article> articleList = jdbcTemplate.query(sql, new ArticleRowMapper(), id);
        return articleList != null && articleList.size() > 0 ? articleList.get(0) : new Article();
    }

    @Override
    public List<Article> getArticleList(Article article, int page, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_article WHERE 1 = 1");
        List<Object> param = new ArrayList<>();

        if (article != null) {
            if (StringUtils.hasText(article.getTitle())) {
                sql.append(" AND TITLE LIKE ?");
                param.add("%" + article.getTitle() + "%");
            }
            if (StringUtils.hasText(article.getLabel())) {
                sql.append(" AND LABEL LIKE ?");
                param.add("%" + article.getLabel() + "%");
            }
            if (StringUtils.hasText(article.getStatus() + "")) {
                sql.append(" AND STATUS = ?");
                param.add(article.getStatus());
            }
            if (StringUtils.hasText(article.getType() + "")) {
                sql.append(" AND TYPE = ?");
                param.add(article.getType());
            }
            if (article.getUser() != null) {
                sql.append(" AND USERID = ?");
                param.add(article.getUser().getId());
            }
        }

        return jdbcTemplate.query(sql.toString() + " limit "+ page + "," + limit, new ArticleRowMapper(), param.toArray());
    }

    @Override
    public int insertArticle(Article article) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) ->
                con.prepareStatement("INSERT INTO tb_article(TITLE,TYPE,CREATETIME,STARTTIME,ENDTIME,STATUS,LABEL,CONTENT)" +
                        " VALUES ('" + article.getTitle() + "'," + article.getType() + ",'" + sdf.format(new Date()) + "','" + article.getStartTime() + "'," +
                        "'" + article.getEndTime() + "'," + article.getStatus() + ",'" + article.getLabel() + "','" + article.getContent() + "')", Statement.RETURN_GENERATED_KEYS)
        , keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateArticle(Article article, boolean status) {
        String sql = "";
        if (status) {//只修改状态
            sql = "UPDATE tb_article SET STATUS = ? WHERE ID = ?";
            return jdbcTemplate.update(sql, article.getStatus(), article.getId());
        }else {//不改变状态
            sql ="UPDATE tb_article SET TITLE=?,TYPE=?,STARTTIME=?,ENDTIME=?,LABEL=?,CONTENT=? WHERE ID = ?";
            return jdbcTemplate.update(sql, article.getTitle(), article.getType(), article.getStartTime(), article.getEndTime(),
                    article.getLabel(), article.getContent(),article.getId());
        }
    }

    @Override
    public int updateLike(Article article) {
        return jdbcTemplate.update("UPDATE tb_article SET LIKECOUNT = LIKECOUNT + 1 WHERE ID = ?", article.getId());
    }

    @Override
    public int updateView(Article article) {
        return jdbcTemplate.update("UPDATE tb_article SET VIEWCOUNT = VIEWCOUNT + 1 WHERE ID = ?", article.getId());
    }

    @Override
    public void deleteArticle(long id) {
        String sql = "DELETE FROM tb_article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Article> getArticleTitleList(int order){
        return jdbcTemplate.query("SELECT ID,TITLE FROM tb_article ORDER BY " + (order == 0 ? "CREATETIME DESC" : "VIEWCOUNT DESC") + " LIMIT 0,5",
                new RowMapper<Article>() {
                    @Override
                    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Article article = new Article();
                        article.setId(rs.getLong("ID"));
                        article.setTitle(rs.getString("TITLE"));
                        return article;
                    }
                });
    }

    private class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            Article article = new Article();
            article.setId(resultSet.getLong("ID"));
            article.setTitle(resultSet.getString("TITLE"));
            article.setType(resultSet.getInt("TYPE"));
            article.setCreateTime(resultSet.getString("CREATETIME"));
            article.setStartTime(resultSet.getString("STARTTIME"));
            article.setEndTime(resultSet.getString("ENDTIME"));
            article.setStatus(resultSet.getInt("STATUS"));
            article.setLabel(resultSet.getString("LABEL"));
            article.setContent(resultSet.getString("CONTENT"));
            article.setViewCount(resultSet.getLong("VIEWCOUNT"));
            article.setLikeCount(resultSet.getLong("LIKECOUNT"));
            return article;
        }
    }
}
