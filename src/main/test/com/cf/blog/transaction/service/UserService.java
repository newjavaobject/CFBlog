package com.cf.blog.transaction.service;

import com.cf.blog.dao.user.UserDAOImpl;
import com.cf.blog.model.user.Role;
import com.cf.blog.model.user.User;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/6/2 0002.
 */
@Service
public class UserService {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    public User queryUserById(long id) {
        String sql = "SELECT * FROM tb_user WHERE id = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return userList != null && userList.size() > 0 ? userList.get(0) : new User();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED,readOnly = true)
    public List<User> queryForList(User user, Role role) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_user WHERE 1 = 1 AND NAME = ?");
        List<Object> param = new ArrayList<>();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        List<User> query = jdbcTemplate.query(sql.toString(), new UserRowMapper(), user.getName());
        c1.signal();
        lock.unlock();
        return query;
    }

    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void insert(User user)  {
        lock.lock();
        jdbcTemplate.update("INSERT INTO tb_user(loginname,name,phone,email,roleid,age) VALUES (?,?,?,?,?,?)",
                user.getLoginName(),user.getName(),user.getPhone(),user.getEmail(),user.getRole().getId(),user.getAge());

        try {
            c1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
//        ((UserService)AopContext.currentProxy()).delete(user);
//        delete(user);
//        throw new RuntimeException("测试事务：");
    }

    @Transactional
    public void delete(User user) {
        jdbcTemplate.update("DELETE FROM tb_user WHERE loginname=?", user.getLoginName());
//        throw new RuntimeException("删除用户异常");
    }

    public void update(User user) {

    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("NAME"));
            return user;
        }
    }
}
