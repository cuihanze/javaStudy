package com.cui.study.netty.shizhan.ch02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClient echoClient = new EchoClient("127.0.0.1", 8080);
        echoClient.start();
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(EchoClientHandler.INSTANCE);
                        }
                    })
                    .remoteAddress(new InetSocketAddress(host, port));
            ChannelFuture f = bootstrap.connect().sync();
            f.channel().closeFuture().sync();
            System.out.println("关闭连接");
        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
