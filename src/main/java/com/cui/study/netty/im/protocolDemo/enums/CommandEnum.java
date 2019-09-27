package com.cui.study.netty.im.protocolDemo;

import lombok.Getter;

public enum CommandEnum {
    LOGIN_REQUEST((byte) 1),
    ;


    @Getter
    private Byte command;

    CommandEnum(Byte command) {
        this.command = command;
    }
}
