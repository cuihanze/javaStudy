package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.Session;
import com.cui.java.study.netty.im.protocolDemo.packet.request.MessageRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.MessageResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());

        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
        // ByteBuf byteBuf = PacketCodeC.encode(messageResponsePacket);

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
        }

    }
}
