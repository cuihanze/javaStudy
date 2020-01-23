package com.cui.study.base.io.test.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞 - 多线程服务器
 */
public class MultiThreadBIOServer {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        try {
            // 监听 8080 端口
            ServerSocket server = new ServerSocket(PORT);
            Socket socket = null;
            while (true) {
                // 阻塞监听客户端连接
                socket = server.accept();
                // 每个客户端请求，服务端都创建一个新的线程处理和客户端的交互
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            // 错误处理
            e.printStackTrace();
        }
    }
}

/**
 * 服务器处理逻辑
 */
class ServerHandler implements Runnable {
    private static final int CAPACITY = 1024;
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] buf = new byte[CAPACITY];
            int len;
            // 阻塞read操作， 等待客户端传来的数据
            while ((len = inputStream.read(buf)) != -1) {
                // 读取数据并输出到控制台
                System.out.println(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
