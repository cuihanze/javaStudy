package com.cui.study.netty.im.protocolDemo.packet.response;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private int code;
    private String reason;

    @Override
    public Byte getCommand() {
        return CommandConstant.LOGIN_RESPONSE;
    }
}
