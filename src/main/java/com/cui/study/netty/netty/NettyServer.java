package com.cui.study.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();// 监听端口，accept 新连接的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();// 处理每一条连接的数据读写的线程组

        ServerBootstrap serverBootstrap = new ServerBootstrap();// 引导类
        serverBootstrap
                .group(boss, worker)// boss监听端口，接收新连接；worker负责读写
                .channel(NioServerSocketChannel.class)// 使用NIO模型，如果使用BIO，此处使用OioServerSocketChannel.class
                .childHandler(new ChannelInitializer<NioSocketChannel>() {// ChannelInitializer 定义后续每条连接的数据读写，业务处理逻辑；NioSocketChannel是对NIO类型连接的抽象
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {// NioSocketChannel是对连接的抽象，类似Socket
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                System.out.println("content:" + s);
                            }
                        });
                    }
                })
                .bind(8000);

    }
}
