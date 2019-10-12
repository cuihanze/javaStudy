package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.constants.CodeConstant;
import com.cui.study.netty.im.protocolDemo.packet.Session;
import com.cui.study.netty.im.protocolDemo.packet.request.LoginRequestPacket;
import com.cui.study.netty.im.protocolDemo.packet.response.LoginResponsePacket;
import com.cui.study.netty.im.protocolDemo.utils.LoginUtil;
import com.cui.study.netty.im.protocolDemo.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的，否则会第二个使用该 handler 的 channel 会报错
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    // 2. 构造单例
    public static final LoginRequestHandler loginRequestHandler = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(msg)) {
            // 校验成功
            msg.setUserId(randomUserId(msg));
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUserName()), ctx.channel());

            // LoginUtil.markAsLogin(ctx.channel());
            loginResponsePacket.setCode(CodeConstant.SUCCESS);
            loginResponsePacket.setUserId(msg.getUserId());
            loginResponsePacket.setUserName(msg.getUserName());
        } else {
            // 校验失败
            loginResponsePacket.setCode(CodeConstant.LOGIN_FAIL);
            loginResponsePacket.setReason("校验失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private String randomUserId(LoginRequestPacket msg) {
        return String.valueOf(msg.getUserName().hashCode());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
