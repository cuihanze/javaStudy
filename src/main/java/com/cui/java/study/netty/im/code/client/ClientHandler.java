package com.cui.java.study.netty.im.code.client;

import com.cui.java.study.netty.im.protocolDemo.constants.CodeConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.request.LoginRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.LoginResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import com.cui.java.study.netty.im.protocolDemo.packet.PacketCodeC;
import com.cui.java.study.netty.im.protocolDemo.packet.response.MessageResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("flash");
        loginRequestPacket.setPassword("pwd");

        // 编码
        ByteBuf byteBuf = PacketCodeC.encode(loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = PacketCodeC.decode((ByteBuf) msg);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.getCode() == CodeConstant.SUCCESS) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("登录成功");
            } else {
                System.out.println(loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(messageResponsePacket.getMessage());
        } else {
            System.out.println("其他处理");
        }
    }
}
