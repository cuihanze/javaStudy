package com.cui.study.netty.im.protocolDemo.packet;

import com.cui.study.netty.im.protocolDemo.enums.CommandEnum;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String userName;
    private String password;

    @Override
    Byte getCommand() {
        return CommandEnum.LOGIN_REQUEST.getCommand();
    }
}
