package com.cui.study.netty.im.protocolDemo;

import lombok.Data;

@Data
public abstract class Packet {
    // 协议版本
    private Byte version = 1;

    // 指令
    abstract Byte getCommand();
}
