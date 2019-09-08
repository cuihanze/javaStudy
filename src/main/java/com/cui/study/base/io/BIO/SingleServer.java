package com.cui.study.base.io.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端 - 单线程版本
 */
public class SingleServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3333);

            while (true) {
                // 阻塞获取连接
                Socket socket = serverSocket.accept();

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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
/*
receive start : main
hello world0
receive end : main
receive start : main
hello world2
receive end : main
receive start : main
hello world3
receive end : main
receive start : main
hello world1
receive end : main
 */
