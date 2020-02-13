package com.cui.java.study.netty.im.code.handler;

import com.cui.java.study.netty.im.protocolDemo.packet.Session;
import com.cui.java.study.netty.im.protocolDemo.packet.request.SendGroupRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.SendGroupResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.utils.GroupSessionUtil;
import com.cui.java.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.List;

public class SendGroupRequestHandler extends SimpleChannelInboundHandler<SendGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupRequestPacket msg) throws Exception {
        List<String> userIds = GroupSessionUtil.getUserIds(msg.getGroupId());
        if (userIds != null) {
            // 创建一个 channel 分组
            ChannelGroup group = new DefaultChannelGroup(ctx.executor());
            for (String userId : userIds) {
                if (userId != msg.getUserId()) {
                    Channel channel = SessionUtil.getChannel(userId);
                    if (channel != null) {
                        group.add(channel);
                    } else {
                        System.out.println("userId:" + userId + "不在线");
                    }
                }
                Session session = SessionUtil.getSession(ctx.channel());
                // 分组发送
                group.writeAndFlush(new SendGroupResponsePacket(session.getUserId() + ": " + session.getUserName() + ": groupId:" + msg.getGroupId() + " -> " + msg.getMsg()));
                SessionUtil.getChannel(msg.getUserId()).writeAndFlush(new SendGroupResponsePacket("发送成功"));
            }
        }
    }
}
