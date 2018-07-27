package com.cf.blog.mybatis.mapper;

import com.cf.blog.mybatis.Classes;
import com.cf.blog.mybatis.Student;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map> getUserList();

    Classes getClass4(int id);

    List<Student> getStudent(int id);
}
