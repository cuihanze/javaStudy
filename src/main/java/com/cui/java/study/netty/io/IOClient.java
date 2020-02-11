package com.cui.java.study.netty.io;

import java.io.IOException;
import java.net.Socket;

public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);
                for (int i = 0; i < 100; i++) {
                    String content = "content " + i;
                    socket.getOutputStream().write(content.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
