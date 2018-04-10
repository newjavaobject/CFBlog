package com.cf.blog.service.user;

import com.cf.blog.dao.user.UserDAO;
import com.cf.blog.model.user.Role;
import com.cf.blog.model.user.User;
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
@Repository("userService")
public class UserService implements IUserService {
    @Resource(name = "userDAO")
    protected UserDAO userDAO;

    @Override
    public User queryUserById(long id) {
        return userDAO.queryUserById(id);
    }

    @Override
    public List<User> queryForList(User user, Role role) {
        return userDAO.queryForList(user, role);
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
