package com.cui.study.base.io.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端 - 多线程版本
 */
public class MultiServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3333);

            while (true) {
                // 阻塞获取连接
                Socket socket = serverSocket.accept();

                // 每个连接用一个新的线程读取数据，保证服务端读取的并发性
                new Thread(() -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        int len;
                        byte[] data = new byte[1024];
                        StringBuilder content = new StringBuilder();
                        while ((len = inputStream.read(data)) != -1) {
                            content.append(new String(data, 0, len));
                        }
                        System.out.println("timestamp:" + System.currentTimeMillis() + " : " + content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}