package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.request.HeartBeatRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        System.out.println(requestPacket.getMsg());
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}

