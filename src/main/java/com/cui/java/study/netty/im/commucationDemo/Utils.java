package com.cui.java.study.netty.im.commucationDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

public class Utils {
    public static ByteBuf getByteBuf(ChannelHandlerContext context, String content) {
        // 获取二进制抽象 ByteBuf
        ByteBuf buf = context.alloc().buffer();
        // 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));
        // 填充数据到 ByteBuf
        buf.writeBytes(bytes);

        return buf;
    }
}
