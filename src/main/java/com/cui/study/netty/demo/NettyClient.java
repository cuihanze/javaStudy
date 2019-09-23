package com.cui.study.netty.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class NettyClient {
    public static void main(String[] args) throws IOException {
        // 获得选择器
        Selector selector = SelectorProvider.provider().openSelector();
        // 打开服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 配置 channel
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// 注册selector到channel
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9999));

        while (true) {
            // 返回次选择器上已选择键集
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannle = server.accept();

                } else if (key.isReadable()) {

                } else if (key.isWritable()) {

                } else if (key.isConnectable()) {
                    System.out.println("connect");
                }
            }
        }

    }
}
