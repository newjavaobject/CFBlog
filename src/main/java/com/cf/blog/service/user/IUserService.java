package com.cf.blog.service.user;

import com.cf.blog.model.user.Role;
import com.cf.blog.model.user.User;

import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/7 0007.
 */
public interface IUserService {
    /** 查询单个用户详情 */
    User queryUserById(long id);

    /** 查询用户列表 */
    List<User> queryForList(User user, Role role);

    /** 新增用户 */
    void insert(User user);

    /** 删除用户 */
    void delete(User user);

    /** 更新用户 */
    void update(User user);
}
