package com.cui.study.netty.im.protocolDemo.packet.request;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.packet.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return CommandConstant.LOGIN_REQUEST;
    }
}
