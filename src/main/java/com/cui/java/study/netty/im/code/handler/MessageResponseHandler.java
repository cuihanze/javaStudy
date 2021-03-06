package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();

        System.out.println(fromUserId + ":" + fromUserName + " -> " + msg .getMessage());
    }
}
