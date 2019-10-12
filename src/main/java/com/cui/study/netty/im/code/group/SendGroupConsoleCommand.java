package com.cui.study.netty.im.code.group;

import com.cui.study.netty.im.protocolDemo.packet.request.SendGroupRequestPacket;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        scanner = new Scanner(System.in);
        System.out.println("请选择群组：");
        String groupId = scanner.nextLine();
        System.out.println("请输入待发送的内容：");
        String msg = scanner.nextLine();
        SendGroupRequestPacket sendGroupRequestPacket = new SendGroupRequestPacket();
        sendGroupRequestPacket.setGroupId(groupId);
        sendGroupRequestPacket.setUserId(SessionUtil.getSession(channel).getUserId());
        sendGroupRequestPacket.setMsg(msg);
        channel.writeAndFlush(sendGroupRequestPacket);
    }
}
