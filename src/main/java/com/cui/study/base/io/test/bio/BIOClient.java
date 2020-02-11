package com.cui.study.base.io.test.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 阻塞 - 客户端
 */
public class BIOClient {
    private static final int PORT = 8080;
    private static final String HOST = "127.0.01";
    private static final int SIZE = 100000000;

    public static void main(String[] args) {
        try {
            // 创建 socket 链接
            Socket socket = new Socket(HOST, PORT);
            Thread.sleep(1);
            for (int i = 0; i < SIZE; i++) {
                // 向服务器发送数据
                Thread.sleep(1);
                socket.getOutputStream().write((Thread.currentThread() + ":hello world" + i).getBytes());
            }
        } catch (IOException | InterruptedException e) {
            // 报错信息打印
            e.printStackTrace();
        }
    }
}
