package com.cf.blog.mybatis;

import com.cf.blog.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.simple.JSONArray;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {
    @Test
    public void mybatisTest() throws IOException {
        InputStream is = Resources.getResourceAsStream("MyBatis-Configuration.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);

        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        Classes class4 = mapper.getClass4(1);
        System.out.println(class4.getStudents());

        System.out.println("111");
        session.close();
    }
}
