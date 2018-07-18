//package com.cf.blog.netty.server;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.LineBasedFrameDecoder;
//import io.netty.handler.codec.string.StringDecoder;
//
//import java.util.Date;
//
//public class NettyTimeServer {
//    public static void main(String[] args) throws Exception {
//        new NettyTimeServer().bind(1111);
//    }
//
//    public void bind(int port) throws Exception {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap.group(bossGroup, workGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childHandler(new ChildChannelHandler());
//            ChannelFuture future = bootstrap.bind(port).sync();//绑定端口，同步等待成功
//            future.channel().closeFuture().sync();//等待服务端监听端口关闭
//        } finally {
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//        }
//    }
//}
//
//class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
//
//    @Override
//    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//        socketChannel.pipeline().addLast(new StringDecoder());
//        socketChannel.pipeline().addLast(new TimeServerHandler());
//    }
//}
//
//class TimeServerHandler extends ChannelHandlerAdapter {
//    private int counter = 0;
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
////        ByteBuf byteBuf = (ByteBuf) msg;
////        byte[] bytes = new byte[byteBuf.readableBytes()];
////        byteBuf.readBytes(bytes);
////        String body = new String(bytes, "UTF-8").substring(0, bytes.length - System.getProperty("line.separator").length());
////
////        String resp = "QRY TIME ORDER".equals(body) ? new Date().toString() : "BAD ORDER";
////        resp += System.getProperty("line.separator");
////        System.out.println("接收到请求：" + body + "，计数器为：" + ++counter);
////
////        ByteBuf responseBuf = Unpooled.copiedBuffer(resp.getBytes());
////        ctx.write(responseBuf);
//        String body = (String) msg;
//        System.out.println("接收到请求：" + body + "，计数器为：" + ++counter);
//
//        String resp = "QRY TIME ORDER".equals(body) ? new Date().toString() : "BAD ORDER";
//        resp += System.getProperty("line.separator");
//        ByteBuf responseBuf = Unpooled.copiedBuffer(resp.getBytes());
//        ctx.write(responseBuf);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//}