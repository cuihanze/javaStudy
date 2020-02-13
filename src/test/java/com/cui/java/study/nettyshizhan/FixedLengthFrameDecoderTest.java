package com.cui.java.study.nettyshizhan;

import com.cui.java.study.netty.nettyshizhan.ch09.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            byteBuf.writeByte(i);
        }
        ByteBuf input = byteBuf.duplicate();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());

        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(byteBuf.readBytes(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(byteBuf.readBytes(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(byteBuf.readSlice(3), read);
        read.release();
        assertNull(embeddedChannel.readInbound());
        byteBuf.release();
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3));

        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }
}
