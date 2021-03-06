package com.cui.java.study.netty.im.code.server;

import com.cui.java.study.netty.im.code.handler.*;
import com.cui.java.study.netty.im.code.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new ServerHandler());
                        ch.pipeline()
                                // .addLast(new LifeCycleTestHandler())
                                .addLast(new IMIdleStateHandler())// 空闲检测
                                .addLast(new Spliter())
                                .addLast(PacketCodecHandler.INSTANCE)
                                //.addLast(new PacketDecoder())
                                .addLast(HeartBeatRequestHandler.INSTANCE)// 检测客户端的心跳
                                .addLast(IMHandler.INSTANCE)
                                //.addLast(LoginRequestHandler.loginRequestHandler)
                                //.addLast(new LogoutRequestHandler())
                                .addLast(new AuthHandler());
                                //.addLast(new MessageRequestHandler())
                                //.addLast(new SendGroupRequestHandler())
                                //.addLast(new CreateGroupRequestHandler())
                                //.addLast(new PacketEncoder());
                    }
                });
        bind(bootstrap, 8080);
    }
    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(8080).addListener(future ->{
            if (future.isSuccess()) {
                System.out.println("绑定端口成功");
            } else {
                System.out.println("绑定端口失败");
            }
        });
    }
}
