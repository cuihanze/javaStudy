package com.cui.study.netty.im.code.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

public class Client {
    private static final int MAX_RETRY = 10;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(boss)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8080, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap
                .connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("connect success");
                    } else if (retry == 0) {
                        System.out.println("retry time is 0, connect fail");
                    } else {
                        // 第几次重试
                        int order = (MAX_RETRY - retry) + 1;
                        // 本次重连间隔
                        int delay = 1 << order;
                        bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                    }
                });
    }
}
