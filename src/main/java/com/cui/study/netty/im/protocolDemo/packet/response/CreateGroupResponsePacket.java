package com.cui.study.netty.im.protocolDemo.packet.response;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class CreateGroupResponsePacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return CommandConstant.GROUP_CREATE_RESPONSE;
    }
}
