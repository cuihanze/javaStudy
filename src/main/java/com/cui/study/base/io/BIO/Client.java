package com.cui.study.base.io.BIO;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * 客户端
 */
public class Client {
    private static final int MAX = 5;
    public static void main(String[] args){
        // 模拟多个客户端
        for (int i = 0; i < MAX; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(finalI + " send start");

                    Socket socket = new Socket("127.0.0.1", 3333);
                    String content = "hello world" + finalI;
                    socket.getOutputStream().write(content.getBytes());

                    System.out.println(finalI + " send end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
/*
1 send start
3 send start
2 send start
0 send start
4 send start
2 send end
4 send end
1 send end
0 send end
3 send end
 */