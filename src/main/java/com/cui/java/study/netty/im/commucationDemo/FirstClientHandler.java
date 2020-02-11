package com.cui.java.study.netty.im.commucationDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    // 在客户端连接建立成功之后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取数据
        ByteBuf buf = Utils.getByteBuf(ctx, "世界，你好");
        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    // 客户端读取服务端的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + " : 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
