package com.cui.study.base.io.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器 - 线程池版本
 */
public class ThreadPoolServer {
    private static final ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3333);


            while (true) {
                // 阻塞获取连接
                Socket socket = serverSocket.accept();
                pool.execute(() -> {
                    InputStream inputStream = null;
                    StringBuilder content = new StringBuilder();
                    try {
                        inputStream = socket.getInputStream();

                        int len;
                        byte[] data = new byte[1024];
                        while ((len = inputStream.read(data)) != -1) {
                            content.append(new String(data, 0, len));
                        }
                        System.out.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("timestamp:" + System.currentTimeMillis() + " : " + content);

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}