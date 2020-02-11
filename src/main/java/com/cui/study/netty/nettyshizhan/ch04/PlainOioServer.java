package com.cui.study.netty.nettyshizhan.ch04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOioServer {
    public static void main(String[] args) throws IOException {
        PlainOioServer plainOioServer = new PlainOioServer();
        plainOioServer.server(8080);
    }

    public void server(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(
                        "Accepted connection from " + socket);
                new Thread(()->{
                    try {
                        InputStream inputStream = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = inputStream.read(bytes)) != -1) {
                            System.out.println(new String(bytes, 0, length));
                        }
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write("Hi!\r\n".getBytes(
                                Charset.forName("UTF-8")));
                        outputStream.flush();
                        socket.close();// 发送完，关闭连接
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
