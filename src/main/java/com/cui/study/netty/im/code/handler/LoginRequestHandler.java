package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.constants.CodeConstant;
import com.cui.study.netty.im.protocolDemo.packet.PacketCodeC;
import com.cui.study.netty.im.protocolDemo.packet.request.LoginRequestPacket;
import com.cui.study.netty.im.protocolDemo.packet.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(msg)) {
            // 校验成功
            loginResponsePacket.setCode(CodeConstant.SUCCESS);
        } else {
            // 校验失败
            loginResponsePacket.setCode(CodeConstant.LOGIN_FAIL);
            loginResponsePacket.setReason("校验失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
