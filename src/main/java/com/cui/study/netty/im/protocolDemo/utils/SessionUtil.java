package com.cui.study.netty.im.protocolDemo.utils;

import com.cui.study.netty.im.protocolDemo.constants.AttributeConstant;
import com.cui.study.netty.im.protocolDemo.packet.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserName(), channel);
        channel.attr(AttributeConstant.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserName());
            channel.attr(AttributeConstant.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(AttributeConstant.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(AttributeConstant.SESSION).get();
    }

    public static Channel getChannel(String userName) {
        return userIdChannelMap.get(userName);
    }
}
