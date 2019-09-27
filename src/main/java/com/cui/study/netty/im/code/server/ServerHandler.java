package com.cui.study.netty.im.code.server;

import com.cui.study.netty.im.protocolDemo.enums.CodeEnum;
import com.cui.study.netty.im.protocolDemo.packet.LoginRequestPacket;
import com.cui.study.netty.im.protocolDemo.packet.LoginResponsePacket;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import com.cui.study.netty.im.protocolDemo.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
                loginResponsePacket.setCode(CodeEnum.SUCCESS.getCode());
            } else {
                // 校验失败
                loginResponsePacket.setCode(CodeEnum.LOGIN_FAIL.getCode());
                loginResponsePacket.setReason("校验失败");
            }
            ctx.channel().writeAndFlush(PacketCodeC.encode(loginResponsePacket));
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
