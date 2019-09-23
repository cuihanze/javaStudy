package com.cui.study.base.io.BIO;

import java.io.IOException;
import java.net.Socket;
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        // 模拟2个客户端

        // 客户端1
        new Thread(()->{
            try {
                // 创建新的socket连接
                Socket socket = new Socket("127.0.0.1", 3333);
                // 向服务器发送数据开始
                socket.getOutputStream().write("client1:start, ".getBytes());
                // 客户端1发送数据开始时间戳
                System.out.println("client1 start timestamp:" + System.currentTimeMillis());
                // 休眠10s，模拟客户端发送时间
                Thread.sleep(10000);
                // 向服务器发送数据结束
                socket.getOutputStream().write("client1:end".getBytes());
                // 客户端1发送数据结束时间戳
                System.out.println("client1 end timestamp:" + System.currentTimeMillis());
                // 关闭客户端
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 睡眠10ms，保证客户端1先连接到服务器
        Thread.sleep(10);

        // 客户端2
        new Thread(()->{
            try {
                // 创建新的socket连接
                Socket socket = new Socket("127.0.0.1", 3333);
                // 向服务器发送数据开始
                socket.getOutputStream().write("client2:start, ".getBytes());
                // 客户端2发送数据开始时间戳
                System.out.println("client2 start timestamp:" + System.currentTimeMillis());
                // 休眠0.1s，模拟客户端发送时间
                Thread.sleep(10);
                // 向服务器发送数据结束
                socket.getOutputStream().write("client2:end".getBytes());
                // 客户端2发送数据结束时间戳
                System.out.println("client2 end timestamp:" + System.currentTimeMillis());
                // 关闭客户端
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}