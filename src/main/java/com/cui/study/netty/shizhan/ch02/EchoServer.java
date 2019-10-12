package com.cui.study.netty.shizhan.ch02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        EchoServer echoServer = new EchoServer(8080);
        echoServer.start();

    }

    public void start() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)// 指定NIO模式
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(EchoServerHandler.ENCHO_SERVER);
                        }
                    })
                    .localAddress(new InetSocketAddress(port));
            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
            System.out.println("关闭连接");
        }finally {
            // 关闭 EventLoopGroup 释放所有资源
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }
}
