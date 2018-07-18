//package com.cf.blog.netty.http;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.RandomAccessFile;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.netty.handler.codec.http.HttpMethod.GET;
//import static io.netty.handler.codec.http.HttpResponseStatus.*;
//import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//
///**
// * Netty的http文件服务器
// */
//public class HttpFileServer {
//    private static final int PORT = 1010;//服务端口
//    private static final String URL = "F:";
//    @Test//Aa BB
//    public void te(){
//        Map<String, Object> map = new HashMap<>();
//        map.put("Aa", "Aa");
//        map.put("BB", "BB");
//        System.out.println(map.get("Aa"));
//    }
//
//    public static void main(String[] args) {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap.group(bossGroup, workGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childHandler(new ChannelInitializer<SocketChannel>(){
//                        @Override
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
//                            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
//                            socketChannel.pipeline().addLast("http-encoder", new HttpRequestEncoder());
//                            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//                            socketChannel.pipeline().addLast("file-http-server", new FileServerHandler(URL));
//                        }
//                    });
//            ChannelFuture future = bootstrap.bind(PORT).sync();//绑定端口，同步等待成功
//            future.channel().closeFuture().sync();//等待服务端监听端口关闭
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//        }
//    }
//}
//
//class FileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
//    private String url;
//    public FileServerHandler(String url) {
//        this.url = url;
//    }
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
//        if (!request.getDecoderResult().isSuccess()){
//            sendError(ctx, BAD_REQUEST);
//            return;
//        }
//        if (request.getMethod() != GET) {
//            sendError(ctx, METHOD_NOT_ALLOWED);
//            return;
//        }
//        final String uri = request.getUri();
//        final String path = getPath(uri);
//        if (path == null) {
//            sendError(ctx, FORBIDDEN);
//            return;
//        }
//        File file = new File(path);
//        if (!file.exists() || file.isHidden()) {
//            sendError(ctx, NOT_FOUND);
//            return;
//        }
//
//        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
//        String resp = "";
//        if (file.isDirectory()) {//是文件夹
//            response.headers().set("Content-Type","text/html");
//            resp = "<html>" +
//                    "<head></head>" +
//                    "<body><a href=\"https://www.baidu.com\" target=\"_blank\">百度</a></body>" +
//                    "</html>";
//        }else { //是文件
//            RandomAccessFile raf = new RandomAccessFile(file, "r");
//            long fileLentgh = raf.length();
//            response.headers().set("Content-Length", fileLentgh);
//            String line = "";
//            while ((line = raf.readLine()) != null) {
//                resp += line;
//            }
//        }
//        response.content().readBytes(Unpooled.copiedBuffer(resp.getBytes()));
//        ctx.write(response);
//        ctx.close();
//    }
//
//    private String getPath(String uri) {
//        try {
//            uri = URLDecoder.decode(uri, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            try {
//                uri = URLDecoder.decode(uri, "ISO-8859-1");
//            } catch (UnsupportedEncodingException e1) {
//                e1.printStackTrace();
//                throw new Error();
//            }
//        }
//        if (uri.endsWith("1010") || uri.endsWith("1010/")) return url + File.separator;
//        uri = uri.replaceAll("/", File.separator);
//
//        System.out.println("请求文件路径：" + uri);
//        return url + uri;
//    }
//
//    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
//        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
//        response.headers().set("Content-Type","text/html");
//
//        String resp = "<html>" +
//                      "<head></head>" +
//                      "<body>状态为：" + status.code() + "</body>" +
//                      "</html>";
//
//        response.content().readBytes(Unpooled.copiedBuffer(resp.getBytes()));
//        ctx.write(response);
//
//        ctx.close();
//    }
//}