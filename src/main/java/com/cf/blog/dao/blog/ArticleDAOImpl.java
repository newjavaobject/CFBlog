package com.cf.blog.dao.blog;

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
    public List<Article> getArticleList(Article article) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_article WHERE 1 = 1");
        List<Object> param = new ArrayList<>();

        if (StringUtils.hasText(article.getTitle())) {
            sql.append(" AND TITLE LIKE ?");
            param.add("%" + article.getTitle() + "%");
        }
        if (StringUtils.hasText(article.getLabel())) {
            sql.append(" AND LABEL LIKE ?");
            param.add("%" + article.getLabel() + "%");
        }
        if (StringUtils.hasText(article.getStatus()+"")) {
            sql.append(" AND STATUS = ?");
            param.add(article.getStatus());
        }
        if (StringUtils.hasText(article.getType()+"")) {
            sql.append(" AND TYPE = ?");
            param.add(article.getType());
        }
        if (article.getUser() != null) {
            sql.append(" AND USERID = ?");
            param.add(article.getUser().getId());
        }

        return jdbcTemplate.query(sql.toString(), new ArticleRowMapper(), param.toArray());
    }

    @Override
    public int insertArticle(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> con.prepareStatement("INSERT INTO tb_article(TITLE,TYPE,CREATETIME,STARTTIME,ENDTIME,STATUS,LABEL,CONTENT)" +
                " VALUES ('"+article.getTitle()+"',"+article.getType()+","+article.getCreateTime()+","+article.getStartTime()+"," +
                ""+article.getEndTime()+","+article.getStatus()+",'"+article.getLabel()+"','"+article.getContent()+"')"), keyHolder);
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
                    article.getLabel(), article.getContent());
        }
    }

    @Override
    public void deleteArticle(long id) {
        String sql = "DELETE FROM tb_article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            Article article = new Article();
            article.setId(resultSet.getLong("ID"));
            article.setTitle(resultSet.getString("TITLE"));
            article.setType(resultSet.getInt("TYPE"));
            article.setCreateTime(resultSet.getDate("CREATETIME"));
            article.setStartTime(resultSet.getDate("STARTTIME"));
            article.setEndTime(resultSet.getDate("ENDTIME"));
            article.setStatus(resultSet.getInt("STATUS"));
            article.setLabel(resultSet.getString("label"));
            article.setContent(resultSet.getString("CONTENT"));
            return article;
        }
    }
}
