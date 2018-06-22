package com.cf.blog.netty.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/30 0030.
 */
public class MultipleTimerServer implements Runnable {
    private Selector selector;//多路复用器
    private ServerSocketChannel channel;
    private int port;//端口

    private volatile boolean stop = false;

    public MultipleTimerServer(int port) {
        this.port = port;
        try {
            channel = ServerSocketChannel.open();//开启通道
            channel.configureBlocking(false);//设置为非阻塞
            selector = Selector.open();

            channel.socket().bind(new InetSocketAddress(this.port));//绑定端口
            SelectionKey register = channel.register(selector, SelectionKey.OP_ACCEPT);//注册多路复用器
            System.out.println("The Time Server is start in port:" + this.port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> keySet = selector.selectedKeys();

                SelectionKey key = null;
                Iterator<SelectionKey> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();

                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws Exception {
        if (key.isValid()) {
            if (key.isAcceptable()) {//处理新请求
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc = channel.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);//注册到多路复用器
            }else if (key.isReadable()) {//读取到数据
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = sc.read(buffer);//从SocketChannel中读取1024个字节的数据,非阻塞的，故需进行判断读取到的字节数
                if (read > 0) {
                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("服务器接收到请求：" + body);

                    byte[] response = new Date().toString().getBytes();
                    ByteBuffer allocate = ByteBuffer.allocate(response.length);
                    allocate.put(response);
                    allocate.flip();

                    sc.write(allocate);//异步非阻塞的，不能保证一次写完数据，会出现"写半包"问题,需要注册写操作，此处未实现
                }else if (read < 0) {//没有读取到数据，对方关闭连接
                    key.cancel();
                    sc.close();
                }else {;}//读取到0字节，忽略
            }
        }
    }
}
