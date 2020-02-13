package com.cui.java.study.netty.im.protocolDemo.packet.response;

import com.cui.java.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private int code;
    private String reason;
    private String userId;
    private String userName;

    @Override
    public Byte getCommand() {
        return CommandConstant.LOGIN_RESPONSE;
    }
}
