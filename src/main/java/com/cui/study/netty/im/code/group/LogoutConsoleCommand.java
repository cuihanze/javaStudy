package com.cui.study.netty.im.code.group;

import com.cui.study.netty.im.protocolDemo.packet.request.LogoutRequestPacket;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        logoutRequestPacket.setUserId(SessionUtil.getSession(channel).getUserId());
        channel.writeAndFlush(logoutRequestPacket);
    }
}
