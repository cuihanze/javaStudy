package com.cui.study.netty.im.protocolDemo.enums;

import lombok.Getter;

public enum CommandEnum {
    LOGIN_REQUEST((byte) 1),
    LOGIN_RESPONSE((byte) 2),
    ;


    @Getter
    private Byte command;

    CommandEnum(Byte command) {
        this.command = command;
    }
}
