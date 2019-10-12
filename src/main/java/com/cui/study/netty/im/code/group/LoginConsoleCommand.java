package com.cui.study.netty.im.code.group;

import com.cui.study.netty.im.protocolDemo.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("输入用户名登录: ");
        scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        loginRequestPacket.setUserName(username);

        // 密码使用默认的
        System.out.print("输入登录密码: ");
        String pwd = scanner.nextLine();
        loginRequestPacket.setPassword(pwd);

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
    }
}
