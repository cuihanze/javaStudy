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
                        System.out.println("receive start : " + Thread.currentThread().getName());

                        InputStream inputStream = socket.getInputStream();
                        int len;
                        byte[] data = new byte[1024];
                        StringBuilder content = new StringBuilder();
                        while ((len = inputStream.read(data)) != -1) {
                            content.append(new String(data, 0, len));
                        }
                        System.out.println(content);

                        System.out.println("receive end : " + Thread.currentThread().getName());
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
/*
receive start : Thread-0
receive start : Thread-2
receive start : Thread-1
receive start : Thread-3
receive start : Thread-4
hello world3
receive end : Thread-1
hello world1
receive end : Thread-2
hello world2
receive end : Thread-4
hello world4
receive end : Thread-0
hello world0
receive end : Thread-3
 */
