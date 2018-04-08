package com.cf.blog.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Map> selectIdList(Map map) {
        return template.query("select * from tb_id", new RowMapper<Map>() {
            @Override
            public Map mapRow(ResultSet resultSet, int i) throws SQLException {
                Map map = new HashMap();
                map.put("id", resultSet.getString("id"));
                map.put("name", resultSet.getString("name"));

                return map;
            }
        });
    }
}
