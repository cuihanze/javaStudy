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
                // 获取输入流
                InputStream inputStream = socket.getInputStream();
                int len;
                byte[] data = new byte[1024];
                StringBuilder content = new StringBuilder();
                // 读取数据
                while ((len = inputStream.read(data)) != -1) {
                    content.append(new String(data, 0, len));
                }
                // 输出
                System.out.println("timestamp:" + System.currentTimeMillis() + " : " + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
