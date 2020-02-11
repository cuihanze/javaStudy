package com.cui.java.study.netty.im.protocolDemo.constants;

import com.cui.java.study.netty.im.protocolDemo.packet.Session;
import io.netty.util.AttributeKey;

import java.util.List;

public class AttributeConstant {
    public static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    public static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
    public static AttributeKey<List<String>> GROUP = AttributeKey.newInstance("groups");
}
