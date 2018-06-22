package com.cf.blog.netty.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2018/5/29 0029.
 */
public class TimeClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1111);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(outputStream, true);
        pw.println("paosidjfpoweijf;pasoiejr\\\rnn哈跑\\\r\\\n死得解放oipwerj啊得解放\r\n");

        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = br.readLine()) != null && line.length() != 0) {
            System.out.println(line);
        }

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
