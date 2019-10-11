package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.packet.request.CreateGroupRequestPacket;
import com.cui.study.netty.im.protocolDemo.packet.response.CreateGroupResponsePacket;
import com.cui.study.netty.im.protocolDemo.utils.GroupSessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        String groupId = GroupSessionUtil.createGroup(ctx.channel(), msg.getUserIdList());
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(createGroupResponsePacket);
    }
}
