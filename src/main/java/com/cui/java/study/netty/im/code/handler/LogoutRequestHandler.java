package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.request.LogoutRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.LogoutResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        String userId = msg.getUserId();
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setMsg("登出成功");
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
