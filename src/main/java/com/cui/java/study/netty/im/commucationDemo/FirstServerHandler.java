package com.cui.java.study.netty.im.commucationDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端读取客户端的数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "：服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 服务器回写数据
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = Utils.getByteBuf(ctx, "服务器回写数据：hello world");
        ctx.channel().writeAndFlush(out);
    }
}
