package com.cui.study.base.io.test.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 阻塞 - 线程池服务器
 */
public class ThreadPoolBIOClient {
    private static final int MAX_THREAD_NUM = 10;
    private static final int QUEUE_SIZE = 100;
    private static final int PORT = 8080;

    public static void main(String[] args) {
        // 初始化线程池
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), MAX_THREAD_NUM, 120L, TimeUnit.SECONDS,new ArrayBlockingQueue(QUEUE_SIZE));
        try {
            // 监听 8080 端口
            ServerSocket server = new ServerSocket(PORT);
            Socket socket = null;
            while (true) {
                // 阻塞监听客户端连接
                socket = server.accept();
                // 使用线程池处理客户端请求
                threadPoolExecutor.execute(new ServerHandler1(socket));
            }
        } catch (IOException e) {
            // 错误处理
            e.printStackTrace();
        }
    }
}
/**
 * 服务器处理逻辑
 */
class ServerHandler1 implements Runnable {
    private static final int CAPACITY = 1024;
    private Socket socket;

    public ServerHandler1(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] buf = new byte[CAPACITY];
            int len;
            // 阻塞read操作， 等待客户端传来的数据
            while ((len = inputStream.read(buf)) != -1) {
                // 读取数据并输出到控制台
                System.out.println(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}