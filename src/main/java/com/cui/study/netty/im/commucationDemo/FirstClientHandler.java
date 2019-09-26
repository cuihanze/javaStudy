package com.cui.study.netty.im.commucationDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    // 在客户端连接建立成功之后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取数据
        ByteBuf buf = getByteBuffer(ctx);
        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuffer(ChannelHandlerContext ctx) {
        // 获取二进制抽象 ByteBuf
        ByteBuf buf = ctx.alloc().buffer();
        // 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "世界，你好".getBytes(Charset.forName("utf-8"));
        // 填充数据到 ByteBuf
        buf.writeBytes(bytes);

        return buf;
    }
}
