package com.cf.blog.netty;

import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagePackTest {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("ddddddddddddddddd");
        list.add("fffffffffffffffff");
        list.add("sssssssssssssssss");
        list.add("gggggggggggggggggg");

        MessagePack pack = new MessagePack();
        byte[] bytes = pack.write(list);

        System.out.println("长度：" + bytes.length);

        List<String> read = pack.read(bytes, Templates.tList(Templates.TString));
        System.out.println(read.get(1));
        System.out.println(read.get(3));
    }
}
