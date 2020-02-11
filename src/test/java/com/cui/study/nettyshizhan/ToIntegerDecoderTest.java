package com.cui.study.nettyshizhan;

import com.cui.study.netty.nettyshizhan.ch09.AbsIntegerEncoder;
import com.cui.study.netty.nettyshizhan.ch10.IntegerToStringDecoder;
import com.cui.study.netty.nettyshizhan.ch10.ToIntegerDecoder;
import com.cui.study.netty.nettyshizhan.ch10.ToIntegerDecoder2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class ToIntegerDecoderTest {
    @Test
    public void test() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 3; i < 10; i++) {
            buf.writeByte(i);
        }

        EmbeddedChannel channel = new EmbeddedChannel(
                new ToIntegerDecoder());
        ByteBuf read = buf.duplicate();

        channel.writeInbound(buf);
        int i = channel.readInbound();
        System.out.println(i);
    }

    @Test
    public void test2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 3; i < 10; i++) {
            buf.writeByte(i);
        }

        EmbeddedChannel channel = new EmbeddedChannel(
                new ToIntegerDecoder2());
        ByteBuf read = buf.duplicate();

        channel.writeInbound(buf);
        int i = channel.readInbound();
        System.out.println(i);
    }

    @Test
    public void test3() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new IntegerToStringDecoder());
        channel.writeInbound(1);
        String str = channel.readInbound();
        System.out.println(str);
    }
}
