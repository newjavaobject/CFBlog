package com.cf.blog;

import com.cf.blog.model.user.Role;
import com.cf.blog.transaction.service.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/4/10 0010.
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        Runtime.getRuntime().exec("C:\\Windows\\System32\\shutdown.exe -s -f");
        InputStream ins = null;
        String[] cmd = new String[] { "cmd.exe", "/C", "dir" };  // 命令
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            ins = process.getInputStream();  // 获取执行cmd命令后的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 输出
            }
            int exitValue = process.waitFor();
            System.out.println("返回值：" + exitValue);
            process.getOutputStream().close();  // 不要忘记了一定要关
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test(){
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        User user = factory.getBean(User.class);
        System.out.println("名字:" + user.getName());
    }

    @org.junit.Test
    public void test1(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = ac.getBean(User.class);
        System.out.println(user.getId() + ":" + user.getName());
    }

    @org.junit.Test
    public void testTransation(){
        com.cf.blog.model.user.User user = new com.cf.blog.model.user.User();
        user.setName("name");
        user.setAge(2);
        user.setEmail("email");
        user.setLoginName("loginname");
        user.setPhone("phone");
        Role role = new Role();
        role.setId(4);
        user.setRole(role);


        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = ac.getBean(UserService.class);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                service.insert(user);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(service.queryForList(user,role).get(0).getName());
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        service.insert(user);
//        service.delete(user);
    }
}
