package com.cf.blog.dao.blog;

import com.cf.blog.model.blog.Label;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Repository("labelDAO")
public class LabelDAOImpl implements LabelDAO {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Override
    public Label getLabelById(long id) {
        String sql = "SELECT * FROM tb_article_label WHERE id = ?";
        List<Label> labelList = jdbcTemplate.query(sql, new LabelRowMapper(), id);
        return labelList != null && labelList.size() > 0 ? labelList.get(0) : new Label();
    }

    @Override
    public List<Label> getLabelList(int page, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_article_label ORDER BY COUNT DESC");
        return jdbcTemplate.query(sql.toString() + " LIMIT "+ page + "," + limit, new LabelRowMapper());
    }

    @Override
    public int insertLabel(Label label) {
        List<Label> list = new ArrayList<>(1);
        list.add(label);
        return insertBatchLabel(list);
    }

    @Override
    public int insertBatchLabel(List<Label> list) {
        int[] ints = jdbcTemplate.batchUpdate("INSERT INTO tb_article_label(LABEL,COUNT) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Label label = list.get(i);
                ps.setString(1, label.getLabel());
                ps.setLong(2, 0);
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        return ints.length;//返回新增条数
    }

    @Override
    public int updateLabel(Label label) {
        return jdbcTemplate.update("UPDATE tb_article_label SET COUNT = COUNT + 1 WHERE ID = ?", label.getId());
    }

    @Override
    public void deleteLabel(long id) {
        String sql = "DELETE FROM tb_article_label WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteLabel(String label) {
        String sql = "DELETE FROM tb_article_label WHERE LABEL = ?";
        jdbcTemplate.update(sql, label);
    }

    private class LabelRowMapper implements RowMapper<Label> {
        @Override
        public Label mapRow(ResultSet resultSet, int i) throws SQLException {
            Label label = new Label();
            label.setId(resultSet.getLong("ID"));
            label.setLabel(resultSet.getString("LABEL"));
            label.setCount(resultSet.getLong("COUNT"));
            return label;
        }
    }
}
