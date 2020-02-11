package com.cui.java.study.netty.im.protocolDemo.packet.response;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {
    private String message;
    private String fromUserId;
    private String fromUserName;

    @Override
    public Byte getCommand() {
        return CommandConstant.MESSAGE_RESPONSE;
    }
}
