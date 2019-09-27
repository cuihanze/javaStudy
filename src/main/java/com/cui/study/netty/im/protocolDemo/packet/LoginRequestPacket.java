package com.cui.study.netty.im.protocolDemo;

import com.cui.study.netty.im.protocolDemo.enums.CommandEnum;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private int userId;
    private String userName;
    private String password;

    @Override
    Byte getCommand() {
        return CommandEnum.LOGIN_REQUEST.getCommand();
    }
}
