package com.cui.java.study.netty.im.code.group;

import com.cui.java.study.netty.im.protocolDemo.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入对方的用户名: ");
        String toUserId = scanner.next();
        System.out.print("输入发送的消息: ");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(message, toUserId));
    }
}
