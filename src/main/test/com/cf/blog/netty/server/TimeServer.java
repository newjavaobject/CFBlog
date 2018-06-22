package com.cf.blog.netty.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/29 0029.
 */
public class TimeServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1111);
            Socket socket = null;
            while (true) {//同时接收多个客户端
                socket = serverSocket.accept();
                final Socket finalSocket = socket;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader br = null;
                        PrintWriter pw = null;
                        try {
                            br = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                            pw = new PrintWriter(finalSocket.getOutputStream(), true);

                            StringBuilder sb = new StringBuilder("");
                            String line = "";
                            while ((line = br.readLine()) != null && line.length() != 0) {
                                System.out.println(123);
                                sb.append(line);
                            }
                            System.out.println(sb.toString());

                            pw.println(new Date().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            pw.close();
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                finalSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }
}
