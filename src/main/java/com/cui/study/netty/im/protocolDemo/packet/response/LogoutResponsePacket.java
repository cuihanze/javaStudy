package com.cui.study.netty.im.protocolDemo.packet.response;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class LogoutResponsePacket extends Packet {
    String msg;
    @Override
    public Byte getCommand() {
        return CommandConstant.LOGOUT_RESPONSE;
    }
}
