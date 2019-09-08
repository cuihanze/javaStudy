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
                    System.out.println("receive start : " + Thread.currentThread().getName());
                    InputStream inputStream = null;
                    try {
                        inputStream = socket.getInputStream();

                        int len;
                        byte[] data = new byte[1024];
                        StringBuilder content = new StringBuilder();
                        while ((len = inputStream.read(data)) != -1) {
                            content.append(new String(data, 0, len));
                        }
                        System.out.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("receive end : " + Thread.currentThread().getName());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
/*
receive start : pool-1-thread-1
receive start : pool-1-thread-3
receive start : pool-1-thread-2
hello world3
receive end : pool-1-thread-2
hello world4
receive end : pool-1-thread-1
receive start : pool-1-thread-2
hello world0
receive end : pool-1-thread-3
receive start : pool-1-thread-1
hello world1
 */
