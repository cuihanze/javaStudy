package com.cui.java.study.netty.im.protocolDemo.utils;

import com.cui.java.study.netty.im.protocolDemo.constants.AttributeConstant;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(AttributeConstant.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(AttributeConstant.LOGIN);
        return loginAttr.get() != null;
    }
}
