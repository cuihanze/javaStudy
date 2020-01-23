package com.cui.study.base.io.test.nio;

import sun.jvm.hotspot.oops.ByteField;

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

public class NIOServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1. bossSelector 负责轮询是否有新的连接，服务端监测到新的连接之后，不再创建一个新的线程，而是直接将新连接绑定到 workerSelector 上，这样就不用 IO 模型中 1w 个 while 循环在死等
            Selector bossSelector = Selector.open();
            // 2. workerSelector 负责轮询连接是否有数据可读
            Selector workerSelector = Selector.open();
            // 3. 处理新的 socket 连接
            new Thread(new BossHandler(bossSelector, workerSelector, PORT)).start();
            // 4. 处理 socket 连接的读写等处理
              new Thread(new WorkerHandler(workerSelector)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 处理新客户端连接
 */
class BossHandler implements Runnable {
    private Selector bossSelector;
    private Selector workerSelector;

    public BossHandler(Selector bossSelector, Selector workerSelector, int port) {
        this.bossSelector = bossSelector;
        this.workerSelector = workerSelector;

        try {
            // 1. 服务器启动
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 2. 服务器通道绑定 8080 端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            // 3. 设置为非阻塞态，否则添加到 selector中会报错
            serverSocketChannel.configureBlocking(false);
            // 4. 注册服务器通道到 bossSelector 上，并监听 SelectionKey.OP_ACCEPT 事件
            serverSocketChannel.register(bossSelector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. 阻塞，直到有新的客户端连接请求
                int result = bossSelector.select();
                if (result > 0) {
                    System.out.println("result :" + result);
                    // 2. 获取新的连接事件集合
                    Set<SelectionKey> selectionKeys = bossSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    // 3. 遍历新的连接事件集合
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 4. 这里只处理 SelectionKey.OP_ACCEPT 事件
                        if (key.isAcceptable()) {
                            try {
                                // 5. 获取客户端请求通道
                                SocketChannel workerChannel = ((ServerSocketChannel) key.channel()).accept();
                                // 6. 通道设置成非阻塞态
                                workerChannel.configureBlocking(false);
                                // 7. 添加到 workerSelector 中，workerSelector 选择器处理和客户端的数据交互（读写等）
                                 workerChannel.register(workerSelector, SelectionKey.OP_READ);
                            } finally {
                                // 8. 【注意】已经处理过的事件一定要手动移除，否则会死循环
                                   // iterator.remove();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 处理服务器与客户端的交互，如读、写、断开连接等
 */
class WorkerHandler implements Runnable {
    private static final int CAPACITY = 1024;
    private Selector workerSelector;

    public WorkerHandler(Selector workerSelector) {
        this.workerSelector = workerSelector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. 阻塞 1ms 等待新的触发事件
                // 【注意】这里不能用 select()，否则服务器启动后，select()处于阻塞状态，且占有 publicKeys 锁，必须要通过 register 注册新的事件才能激活，
                // 但 register 方法需获取 publicKeys 锁，造成死锁
                if (workerSelector.select(3000) > 0) {
                    // 2. 获取新的 读、写等 事件集合
                    Set<SelectionKey> selectionKeys = workerSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    // 3. 遍历集合
                    while (iterator.hasNext()) {
                        // 4.获取客户端请求通道
                        SelectionKey key = iterator.next();
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (key.isReadable()) {
                            try {
                                // 5. 处理读请求，【注意】Java NIO 未单独定义客户端关闭请求，客户端关闭也会发送读请求
                                doReadOrClose(channel, key);
                            } finally {
                                // 【注意】已经处理过的事件一定要手动移除，否则会死循环
                                // iterator.remove();
                            }
                        } else if (key.isWritable()) {
                            try {
                                // 6. 处理写请求
                                doWrite(channel);
                            } finally {
                                // 【注意】已经处理过的事件一定要手动移除，否则会死循环
                                iterator.remove();
                                // 【注意】写完后，一定要记得将OP_WRITE事件注销，否则会死循环。
                                 key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                            }

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doWrite(SocketChannel channel)  throws IOException{
        // 1. 向客户端回写数据逻辑，省略
        System.out.println("向客户端回写");
    }

    private void doReadOrClose(SocketChannel readChannel, SelectionKey key) throws IOException {
        // 分配对内缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
        while (true) {
            // 通过 buffer 从通道读取数据
            // 读数据是非阻塞的，无论接收到多少数据，甚至没有数据，都会直接返回
            int flag = readChannel.read(buffer);

            // 如果 flag = -1，表示为客户端断开连接请求
            if (flag == -1) {
                System.out.println("客户端关闭");
                // 客户端已断开链接，服务器也断开对应的通道
                key.cancel();
                readChannel.close();
                return;
            }

            // 如果 flag = 0，表示已读完数据
            if (flag == 0) {
                System.out.println("读取数据完成");
                break;
            }

            // 缓冲区读写模式切换，这里是从写模式切换成都模式
            buffer.flip();
            System.out.println(
                    Charset.defaultCharset().newDecoder().decode(buffer).toString());
            // 清空缓冲区，等待下一次写入
            buffer.clear();
        }

        // 【注意】将通道注册到 workerSelector 上，并设置为 写事件
        // 强烈建议直接用readChannel.write(ByteBuffer src)方法直接向客户端回写数据
        // readChannel.register(workerSelector, SelectionKey.OP_WRITE);
    }
}
