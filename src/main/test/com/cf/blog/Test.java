package com.cf.blog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = ac.getBean(User.class);
        System.out.println(user.getId() + ":" + user.getName());
    }
}
