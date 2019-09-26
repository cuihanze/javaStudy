package com.cui.study.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClientTest {
    private static final int MAX_RETRY = 10;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")// 给 NIOSocketChannel 绑定自定义属性
                // option 设置一些TCP底层相关的属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)// 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                .option(ChannelOption.SO_KEEPALIVE, true)// 表示是否开启 TCP 底层心跳机制，true 为开启
                .option(ChannelOption.TCP_NODELAY, true)// 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
                // 1. 指定线程类型
                .group(workGroup)
                // 2. 执行IO类型为 NIO
                .channel(NioSocketChannel.class)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("处理逻辑");
                    }
                });
        // 4. 建立连接
        connect(bootstrap, "https://www.baidu.com/", 43, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else if (retry == 0) {
                        System.out.println("重试次数已用完，放弃连接");
                    } else {
                        // 第几次重试
                        int order = (MAX_RETRY - retry) + 1;
                        // 本次重连间隔
                        int delay = 1 << order;
                        System.out.println(new Date() + ": 连接失败，第" + order + "次重连……");
                        bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                    }
                });
    }
}
