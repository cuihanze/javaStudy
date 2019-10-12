package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.packet.response.LogoutResponsePacket;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println(msg.getMsg());
    }
}
