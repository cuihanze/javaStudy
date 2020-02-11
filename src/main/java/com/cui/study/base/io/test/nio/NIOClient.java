package com.cui.study.base.io.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    public static void main(String[] args) throws InterruptedException {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("hello world！".getBytes()));
            Thread.sleep(100000000);
            socketChannel.close();

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
