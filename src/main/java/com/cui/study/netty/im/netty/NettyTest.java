package com.cui.study.netty.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyTest {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 启动一个Netty服务端，必须要指定三类属性，分别是线程模型、IO 模型、连接读写处理逻辑，最后通过 bind 绑定端口号
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")// 为 NioServerSocketChannel 添加一些属性，可通过 NioServerSocketChannel 的attr()取出属性
                .handler(new ChannelInitializer<NioServerSocketChannel>() {// 处理服务器启动过程中的逻辑
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        System.out.println("serverName" + nioServerSocketChannel.attr(AttributeKey.newInstance("serverName")));
                        System.out.println("服务器启动中");
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)// option 为服务器设置一系列属性，最常见的是 so_backlog，表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .childAttr(AttributeKey.newInstance("clientKey"), "clientKey")// 为每个连接添加属性
                .childHandler(new ChannelInitializer<NioSocketChannel>() {// 处理连接的读写逻辑
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        System.out.println("clientKey" + nioSocketChannel.attr(AttributeKey.newInstance("clientKey")));
                        System.out.println("处理连接的读写逻辑");
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)// childOption 为每条连接设置TCP底层相关的属性，SO_KEEPALIVE 表示是否开启TCP底层心跳机制，true表示开启
                .childOption(ChannelOption.TCP_NODELAY, true)// 是否开启 Nagle 算法。true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                ;

        bind(bootstrap, 1000);
    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap
                .bind(port)// bind是一个异步的方法，调用之后是立即返回的，他的返回值是一个ChannelFuture，我们可以给这个ChannelFuture添加一个监听器GenericFutureListener，然后我们在GenericFutureListener的operationComplete方法里面
                .addListener(new GenericFutureListener<Future<? super Void>>() {// 添加监听器，绑定结果回调
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.out.println("端口[" + port + "]绑定失败!");
                    bind(bootstrap, port + 1);
                }
            }
        });
    }
}
