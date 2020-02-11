package com.cui.study.netty.nettyshizhan.ch04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {
    public static void main(String[] args) throws IOException {
        new PlainNioServer().server(8080);
    }
    public void server(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);// 设置非阻塞

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println(
                                "Accepted connection from " + client);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int length;
                        while ((length = client.read(buffer)) != -1) {
                            buffer.flip();
                            System.out.println(Charset.defaultCharset().decode(buffer));
                            buffer.clear();
                        }
                        client.register(selector, SelectionKey.OP_WRITE);
                        System.out.println(
                                "Read connection from " + client);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        client.write(ByteBuffer.wrap("Hi!\r\n".getBytes()));
                        System.out.println(
                                "Write connection from " + client);
                        client.close();
                    }
                    iterator.remove();// 处理完移除
                }
            }
        }
    }
}
