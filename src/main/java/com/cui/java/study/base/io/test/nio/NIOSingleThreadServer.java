package com.cui.java.study.base.io.test.nio;

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

public class NIOSingleThreadServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1. selector 负责轮询是否有新的连接，服务端监测到新的连接之后，不再创建一个新的线程，
            Selector selector = Selector.open();

            // 打开服务器通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 通道绑定 8080 端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置为非阻塞态，否则注册到 selector 上会报错
            serverSocketChannel.configureBlocking(false);

            // 服务器通道注册到 selector 上，并设置关注新连接请求事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 阻塞直到有 selector 关注的事件准备就绪
                int numKeys = selector.select();
                if (numKeys == 0) {
                    System.err.println("select wakes up with zero!!!");
                    continue;
                }

                // 获取激活事件的列表
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // 处理新连接请求
                        try {
                            SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                            socketChannel.configureBlocking(false);
                            // 新连接注册到 selector 上
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } finally {
                            // 移除已选择集上的 SelectionKey
                             iterator.remove();
                        }
                    } else if (key.isReadable()) {
                        try {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
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
                        } finally {
                            iterator.remove();
                        }
                    } else if (key.isWritable()) {
                        try {
                            // 写操作
                        }finally {
                            iterator.remove();
                             key.interestOps(key.interestOps() & (~key.readyOps()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
