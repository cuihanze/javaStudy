package com.cui.study.netty.im.code.client;

import com.cui.study.netty.im.code.group.ConsoleCommandManager;
import com.cui.study.netty.im.code.group.LoginConsoleCommand;
import com.cui.study.netty.im.code.handler.*;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client2 {
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
                        // ch.pipeline().addLast(new ClientHandler());
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast(PacketCodecHandler.INSTANCE)
                                // .addLast(new LoginHandler())
                                //.addLast(new PacketDecoder())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new SendGroupResponseHandler())
                                .addLast(new CreateGroupResponseHandler());
                                //.addLast(new PacketEncoder());
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

                        Channel channel = ((ChannelFuture) future).channel();
                        // 连接成功之后，启动控制台线程
                        startConsoleThread(channel);
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

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
                    consoleCommandManager.exec(scanner, channel);
                }
                waitForResponse();

                /*if (!SessionUtil.hasLogin(channel)) {
                    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                    System.out.print("输入用户名登录: ");
                    String username = sc.nextLine();
                    loginRequestPacket.setUserName(username);

                    // 密码使用默认的
                    System.out.print("输入登录密码: ");
                    String pwd = sc.nextLine();
                    loginRequestPacket.setPassword(pwd);

                    // 发送登录数据包
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                } else {
                    System.out.print("输入对方的用户名: ");
                    String toUserName = sc.next();
                    System.out.print("输入发送的消息: ");
                    String message = sc.next();
                    channel.writeAndFlush(new MessageRequestPacket(message, toUserName));
                }
*/
                /*if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端：");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setMessage(line);
                    ByteBuf byteBuf = PacketCodeC.encode(messageRequestPacket);
                    channel.writeAndFlush(byteBuf);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                 }*/
            }
        }).start();
    }

    private static void waitForResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
