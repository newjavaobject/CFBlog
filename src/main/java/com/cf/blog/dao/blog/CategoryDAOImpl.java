package com.cf.blog.dao.blog;

import com.cf.blog.model.blog.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Override
    public Category getCategoryById(String id) {
        String sql = "SELECT * FROM tb_article_category WHERE id = ?";
        List<Category> categoryList = jdbcTemplate.query(sql, new CategoryRowMapper(), id);
        return categoryList != null && categoryList.size() > 0 ? categoryList.get(0) : new Category();
    }

    @Override
    public List<Category> getCategoryList(Category category, int page, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_article_category WHERE 1 = 1");
        List<Object> param = new ArrayList<>();

        if (category != null) {
            if (category.getName() != null) {
                sql.append(" AND (NAME = ? OR REMARK = ?)");
                param.add(category.getName());
                param.add(category.getName());
            }
        }

        return jdbcTemplate.query(sql.toString() + " limit "+ page + "," + limit, new CategoryRowMapper(), param.toArray());
    }

    @Override
    public int insertCategory(Category category) {
        return jdbcTemplate.update((Connection con) ->
                con.prepareStatement("INSERT INTO tb_article_category(ID,NAME,REMARK)" +
                        " VALUES ('" + category.getId() + "'," + category.getName() + ",'"+category.getRemark()+"')")
        );
    }

    @Override
    public int updateCategory(Category category) {
        String sql = "UPDATE tb_article_category SET NAME = ?, REMARK=? WHERE ID = ?";
        return jdbcTemplate.update(sql, category.getName(), category.getRemark(), category.getId());
    }

    @Override
    public void deleteCategory(String id) {
        String sql = "DELETE FROM tb_article_category WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    private class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getString("ID"));
            category.setName(resultSet.getString("NAME"));
            category.setRemark(resultSet.getString("REMARK"));
            return category;
        }
    }
}
