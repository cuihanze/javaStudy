package com.cui.study.netty.im.protocolDemo.packet.request;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class LogoutRequestPacket extends Packet {
    String userId;
    @Override
    public Byte getCommand() {
        return CommandConstant.LOGOUT_REQUEST;
    }
}
