package com.cui.study.netty.im.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            // 阻塞获取新的连接
            Socket socket = serverSocket.accept();
            // 新开线程处理新的连接
            new Thread(()->{
                try {
                    // 获取客户端数据
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int length = -1;
                    while ((length = inputStream.read(buffer)) != -1) {
                        System.out.println(new String(buffer, 0, length));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
