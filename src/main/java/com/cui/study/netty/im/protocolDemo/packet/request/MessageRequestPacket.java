package com.cui.study.netty.im.protocolDemo.packet.request;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return CommandConstant.MESSAGE_REQUEST;
    }
}
