package com.cui.java.study.base.io.test.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 阻塞 - 客户端
 */
public class BIOClient2 {
    private static final int PORT = 8080;
    private static final String HOST = "127.0.01";
    private static final int SIZE = 100;

    public static void main(String[] args) {
        try {
            // 创建 socket 链接
            Socket socket = new Socket(HOST, PORT);
            Thread.sleep(1000);
            for (int i = 0; i < SIZE; i++) {
                // 向服务器发送数据
                socket.getOutputStream().write((Thread.currentThread() + ":hello world" + i + "\n").getBytes());
            }
        } catch (IOException | InterruptedException e) {
            // 报错信息打印
            e.printStackTrace();
        }
    }
}
