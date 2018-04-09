package com.cf.blog.dao.user;

import com.cf.blog.pojo.user.Role;
import com.cf.blog.pojo.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Override
    public User queryUserById(long id) {
        String sql = "SELECT * FROM tb_user WHERE id = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return userList != null && userList.size() > 0 ? userList.get(0) : new User();
    }

    @Override
    public List<User> queryForList(User user, Role role) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_user WHERE 1 = 1");
        List<Object> param = new ArrayList<>();

        if (StringUtils.hasText(user.getLoginName())) {
            sql.append(" AND LOGINNAME LIKE ?");
            param.add("%" + user.getLoginName() + "%");
        }
        if (StringUtils.hasText(user.getName())) {
            sql.append(" AND NAME LIKE ?");
            param.add("%" + user.getName() + "%");
        }
        if (StringUtils.hasText(user.getPhone())) {
            sql.append(" AND PHONE LIKE ?");
            param.add("%" + user.getPhone() + "%");
        }
        if (StringUtils.hasText(user.getEmail())) {
            sql.append(" AND EMAIL LIKE ?");
            param.add("%" + user.getEmail() + "%");
        }
        if (role != null) {
            sql.append(" AND ROLEID = ?");
            param.add(role.getId());
        }

        return jdbcTemplate.query(sql.toString(), new UserRowMapper(), param.toArray());
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            return user;
        }
    }
}
