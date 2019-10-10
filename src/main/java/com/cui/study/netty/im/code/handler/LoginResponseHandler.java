package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.constants.CodeConstant;
import com.cui.study.netty.im.protocolDemo.packet.Session;
import com.cui.study.netty.im.protocolDemo.packet.response.LoginResponsePacket;
import com.cui.study.netty.im.protocolDemo.utils.LoginUtil;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.getCode() == CodeConstant.SUCCESS) {
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUserName()), ctx.channel());

            // LoginUtil.markAsLogin(ctx.channel());
            System.out.println("登录成功");
        } else {
            System.out.println(msg.getReason());
        }
    }
}