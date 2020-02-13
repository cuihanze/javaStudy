package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.response.SendGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SendGroupResponseHandler extends SimpleChannelInboundHandler<SendGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupResponsePacket msg) throws Exception {
        System.out.println(msg.getMsg());
    }
}
