package com.cui.java.study.netty.im.protocolDemo.packet.request;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class HeartBeatRequestPacket extends Packet {
    private String msg = "心跳请求消息";
    @Override
    public Byte getCommand() {
        return CommandConstant.HEARTBEAT_REQUEST;
    }
}
