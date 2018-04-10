package com.cf.blog.controller.blog;

import com.cf.blog.model.JsonResult;
import com.cf.blog.model.Result;
import com.cf.blog.model.user.User;
import com.cf.blog.service.user.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenzhiyu on 2018/4/10 0010.
 */
@Controller
@RequestMapping("/user")//用户模块
public class UserController {
    @Resource(name = "userService")
    private IUserService userService;

    /** 新增用户 */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult<String> addUser(@RequestBody User user){
        userService.insert(user);
        return new JsonResult<>(Result.SUCCESS.status, Result.SUCCESS.msg, "新增用户成功");
    }

    /** 删除用户 */
    @RequestMapping("/del/{id}")
    @ResponseBody
    public JsonResult<String> delUser(@PathVariable("id") long id){
        userService.delete(new User());
        return new JsonResult<>(Result.SUCCESS.status, Result.SUCCESS.msg, "删除用户成功");
    }

    /** 更新用户 */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public JsonResult<String> updateUser(@PathVariable("id") User user){
        userService.update(user);
        return new JsonResult<>(Result.SUCCESS.status, Result.SUCCESS.msg, "编辑用户成功");
    }

    /** 获取一个用户信息 */
    @RequestMapping("/get/{id}")
//    @ResponseBody  --  返回html页面
    public String getUser(@PathVariable long id){
        User user = userService.queryUserById(id);
        if (null == user) {
            user = new User();
        }

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("user", user);
        return "blog_detail";
    }

    /** 根据条件获取用户列表 */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult<List<User>> getUserList(@RequestBody User user){
        List<User> userList = userService.queryForList(user, null);
        if (null != userList) {
            return new JsonResult<>(Result.SUCCESS.status, Result.SUCCESS.msg, userList);
        } else {
            return new JsonResult<>(Result.FAILED.status, Result.FAILED.msg, null);
        }
    }
}
