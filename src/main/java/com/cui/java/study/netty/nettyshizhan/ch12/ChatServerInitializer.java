package com.cui.java.study.netty.nettyshizhan.ch12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel> {    //1
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {            //2
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());// 将请求和应答消息解码为HTTP消息
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));// 将HTTP消息的多个部分合成一条完整的HTTP消息
        pipeline.addLast(new ChunkedWriteHandler());// 向客户端发送HTML5文件
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
