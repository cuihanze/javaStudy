package com.cui.study.netty.im.protocolDemo.packet.response;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return CommandConstant.MESSAGE_RESPONSE;
    }
}
