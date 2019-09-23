package com.cui.study.base.io.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 非阻塞，两个线程，一个读
 */
public class NIOServer {
    public static void main(String[] args) {
        try {
            Selector serverSelector = Selector.open();
            Selector clientSelector = Selector.open();

            new Thread(() -> {
                try {
                    ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                    listenerChannel.socket().bind(new InetSocketAddress(3333));
                    listenerChannel.configureBlocking(false);
                    listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                    while (true) {
                        if (serverSelector.select(1) > 0) {
                            Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                            Iterator<SelectionKey> iterator = selectionKeys.iterator();
                            while (iterator.hasNext()) {
                                SelectionKey selectionKey = iterator.next();
                                if (selectionKey.isAcceptable()) {
                                    try {
                                        SocketChannel clientChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                        clientChannel.configureBlocking(false);
                                        clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                    }finally {
                                        iterator.remove();
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


            new Thread(() -> {
                while (true) {
                    try {
                        if (clientSelector.select(1) > 0) {
                            Set<SelectionKey> selectionKeys = clientSelector.selectedKeys();
                            Iterator<SelectionKey> iterator = selectionKeys.iterator();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            while (iterator.hasNext()) {
                                SelectionKey selectionKey = iterator.next();
                                if (selectionKey.isReadable()) {
                                    try {
                                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();

                                        while (clientChannel.read(buffer) > 0) {
                                            // 写切换到读
                                            buffer.flip();
                                            System.out.println(Charset.defaultCharset().newDecoder().decode(buffer).toString());

                                            // 读完切换成写模式，能让管道继续读取文件的数据
                                            buffer.clear();
                                        }

                                        // 读取完，关闭 clientSocket
                                        clientChannel.close();
                                    }finally {
                                        iterator.remove();
                                        // selectionKey.interestOps(SelectionKey.OP_READ);
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
