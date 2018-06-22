package com.cf.blog.netty.server;

/**
 * Created by Administrator on 2018/5/29 0029.
 * NIO服务器端
 */
public class NioTimeServer {
    public static void main(String[] args) {
        new Thread(new MultipleTimerServer(1111)).start();
    }
}
