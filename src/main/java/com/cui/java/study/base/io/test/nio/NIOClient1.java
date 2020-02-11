package com.cui.java.study.base.io.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient1 {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    public static void main(String[] args) throws InterruptedException {
        try {
            // 1. 获取通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            // 1.1切换成非阻塞模式
            socketChannel.configureBlocking(false);
            /*for (int i = 0; i < 1000; i++) {
                Thread.sleep(1000);
                socketChannel.write(ByteBuffer.wrap((i + "").getBytes()));
            }*/
            socketChannel.write(ByteBuffer.wrap("hello".getBytes()));
            Thread.sleep(100000000);
            // Thread.sleep(111111);
            /*ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
            socketChannel.write(buffer);
            System.out.println("finish");
            Thread.sleep(30);

            buffer = ByteBuffer.wrap("hello world 2".getBytes());
            socketChannel.write(buffer);*/

             socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
