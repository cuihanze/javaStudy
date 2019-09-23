package com.cui.study.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);// 线程池，监听连接事件
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);// 线程池，处理客户端读写事件

        ServerBootstrap serverBootstrap = new ServerBootstrap();// serverBootstrap 包括了服务器所有配置

        //配置服务器
        serverBootstrap.group(bossGroup, workerGroup)// 配置线程池
                .channel(NioServerSocketChannel.class)// 配置Channel
                .option(ChannelOption.SO_BACKLOG, 100)// 对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列
                .handler(new EchoServerHandler())// 配置bossGroup业务处理逻辑
                .childHandler(new ChannelInitializer<SocketChannel>() {//配置workerGroup业务处理逻辑

                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }

                });

        // 启动Server
        ChannelFuture f = serverBootstrap.bind(9999).sync();

        // 阻塞，直到server socket 关闭
        f.channel().closeFuture().sync();
    }
}
