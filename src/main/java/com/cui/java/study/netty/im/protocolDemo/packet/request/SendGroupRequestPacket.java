package com.cui.java.study.netty.im.protocolDemo.packet.request;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class SendGroupRequestPacket extends Packet {
    private String userId;
    private String groupId;
    private String msg;

    @Override
    public Byte getCommand() {
        return CommandConstant.GROUP_SEND_REQUEST;
    }
}
