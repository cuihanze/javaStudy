package com.cui.java.study.base.io.test.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOBlockServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // 打开服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 通道绑定 8080 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        // 设置为非阻塞态，否则注册到 selector 上会报错
        serverSocketChannel.configureBlocking(true);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int flag = socketChannel.read(buffer);
                if (flag == -1) {
                    System.out.println("客户端关闭");
                    socketChannel.close();
                    break;
                }

                if (flag == 0) {
                    System.out.println("读取数据完成");
                    break;
                }

                buffer.flip();
                System.out.println(
                        Charset.defaultCharset().newDecoder().decode(buffer).toString());
                buffer.clear();
            }
        }
    }
}
