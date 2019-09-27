package com.cui.study.netty.im.protocolDemo.enums;

import lombok.Getter;

public enum CodeEnum {
    SUCCESS(200),
    LOGIN_FAIL(201),
    ;

    @Getter
    private int code;

    CodeEnum(int code) {
        this.code = code;
    }
}
