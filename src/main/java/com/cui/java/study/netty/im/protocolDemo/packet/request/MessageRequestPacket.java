package com.cui.java.study.netty.im.protocolDemo.packet.request;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {
    private String message;
    private String toUserId;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String message, String toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    @Override
    public Byte getCommand() {
        return CommandConstant.MESSAGE_REQUEST;
    }
}
