package com.cui.study.netty.im.protocolDemo.enums;

import lombok.Getter;

public enum SerializerEnum {
    JSON((byte) 1),
    ;
    @Getter
    private byte type;

    SerializerEnum(byte type) {
        this.type = type;
    }
}
