package com.cui.study.netty.im.protocolDemo.packet;

import com.cui.study.netty.im.protocolDemo.enums.CommandEnum;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private int code;
    private String reason;

    @Override
    Byte getCommand() {
        return CommandEnum.LOGIN_RESPONSE.getCommand();
    }
}
