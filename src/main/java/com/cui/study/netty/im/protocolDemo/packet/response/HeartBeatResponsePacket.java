package com.cui.study.netty.im.protocolDemo.packet.response;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class HeartBeatResponsePacket extends Packet {
    private String msg = "心跳回复消息";
    @Override
    public Byte getCommand() {
        return CommandConstant.HEARTBEAT_RESPONSE;
    }
}
