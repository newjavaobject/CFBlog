package com.cf.blog.netty.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/29 0029.
 * NIO客户端
 */
public class NioTimeClient implements Runnable {
    private volatile boolean stop = false;
    public static void main(String[] args) {
        new Thread(new NioTimeClient(), "nio-client").start();
    }

    @Override
    public void run() {
        SocketChannel sc = null;
        Selector selector = null;
        try {
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);//非阻塞

            /* 连接 */
            if(sc.connect(new InetSocketAddress("127.0.0.1", 1111))){
                sc.register(selector, SelectionKey.OP_READ);//如果直接连接成功
                doWrite(sc);//发送消息
            }else {
                sc.register(selector, SelectionKey.OP_CONNECT);
            }

            while (!stop) {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key, selector);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleInput(SelectionKey key, Selector selector) throws Exception {
        if (key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (channel.finishConnect()) {
                    channel.register(selector, SelectionKey.OP_READ);
                    doWrite(channel);
                }else {
                    System.exit(1);
                }
            }else if (key.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = channel.read(buffer);
                if (readBytes > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    String body = new String(bytes, "UTF-8");
                    System.out.println("接收到服务器时间：" + body);

                    this.stop = true;
                }else if (readBytes < 0) {
                    key.cancel();
                    channel.close();
                }else {;}
            }
        }
    }

    private void doWrite(SocketChannel channel) {
        byte[] req = "query".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(req.length);
        buffer.put(req);
        buffer.flip();

        try {
            channel.write(buffer);
            if (!buffer.hasRemaining()) {
                System.out.println("发送query消息成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
