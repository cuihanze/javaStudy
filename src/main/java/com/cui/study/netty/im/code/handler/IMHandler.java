package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

// 压缩 handler - 合并平行 handler
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    public IMHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(CommandConstant.LOGOUT_REQUEST, new LogoutRequestHandler());
        handlerMap.put(CommandConstant.LOGIN_REQUEST, LoginRequestHandler.loginRequestHandler);
        handlerMap.put(CommandConstant.MESSAGE_REQUEST, new MessageRequestHandler());
        handlerMap.put(CommandConstant.GROUP_SEND_REQUEST, new SendGroupRequestHandler());
        handlerMap.put(CommandConstant.GROUP_CREATE_REQUEST, new CreateGroupRequestHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
