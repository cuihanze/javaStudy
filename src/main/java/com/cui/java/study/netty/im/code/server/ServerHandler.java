package com.cui.java.study.netty.im.code.server;

import com.cui.java.study.netty.im.protocolDemo.constants.CodeConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.request.LoginRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.request.MessageRequestPacket;
import com.cui.java.study.netty.im.protocolDemo.packet.response.LoginResponsePacket;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import com.cui.java.study.netty.im.protocolDemo.packet.PacketCodeC;
import com.cui.java.study.netty.im.protocolDemo.packet.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 解码
        Packet packet = PacketCodeC.decode((ByteBuf) msg);

        // 判断是否是登录请求的数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setCode(CodeConstant.SUCCESS);
            } else {
                // 校验失败
                loginResponsePacket.setCode(CodeConstant.LOGIN_FAIL);
                loginResponsePacket.setReason("校验失败");
            }
            ctx.channel().writeAndFlush(PacketCodeC.encode(loginResponsePacket));
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf byteBuf = PacketCodeC.encode(messageResponsePacket);

            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
