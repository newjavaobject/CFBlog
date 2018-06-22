package com.cf.blog.netty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * 测试java内置和其他框架的 编、解码
 * jdk:不跨语言、编码后码流大，网络传输和存储成本高
 */
public class de_en_code {
    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo("chenzhiyu", 20);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os =  new ObjectOutputStream(outputStream);
        os.writeObject(info);
        os.flush();
        os.close();

        byte[] bytes = outputStream.toByteArray();
        System.out.println("JDK length:" + bytes.length);

        System.out.println("byte array length:" + info.code().length);
    }
}

class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;
    public UserInfo(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //编码
    public byte[] code(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = this.name.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);

        buffer.putInt(this.age);
        buffer.flip();

        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
