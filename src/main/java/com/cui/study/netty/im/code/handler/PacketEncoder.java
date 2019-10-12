package com.cui.study.netty.im.code.handler;

import com.cui.study.netty.im.protocolDemo.packet.Packet;
import com.cui.study.netty.im.protocolDemo.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.encode(out, msg);
    }
}
