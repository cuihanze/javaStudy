package com.cui.study.netty.im.code.server;

import com.cui.study.netty.im.code.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

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
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LogoutRequestHandler())
                                .addLast(LoginRequestHandler.loginRequestHandler)
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new SendGroupRequestHandler())
                                .addLast(new CreateGroupRequestHandler())
                                .addLast(new PacketEncoder());
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
