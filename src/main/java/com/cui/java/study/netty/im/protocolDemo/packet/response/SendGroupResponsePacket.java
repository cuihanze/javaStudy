package com.cui.java.study.netty.im.protocolDemo.packet.response;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class SendGroupResponsePacket extends Packet {
    String msg;

    public SendGroupResponsePacket(String msg) {
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return CommandConstant.GROUP_SEND_RESPONSE;
    }
}
