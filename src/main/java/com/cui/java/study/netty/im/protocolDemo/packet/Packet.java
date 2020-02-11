package com.cui.java.study.netty.im.protocolDemo.packet;

import lombok.Data;

@Data
public abstract class Packet {
    // 魔数
    private static final int MAGIC_NUMBER = 0x12345678;

    public static int getMagicNumber() {
        return MAGIC_NUMBER;
    }

    // 协议版本
    private Byte version = 1;

    // 指令
    public abstract Byte getCommand();
}
