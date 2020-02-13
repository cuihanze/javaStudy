package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        System.out.println(msg.getMsg());
    }
}
