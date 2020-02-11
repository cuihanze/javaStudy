package com.cui.java.study.netty.im.protocolDemo.utils;

import com.cui.java.study.netty.im.protocolDemo.constants.AttributeConstant;
import com.cui.java.study.netty.im.protocolDemo.packet.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(AttributeConstant.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(AttributeConstant.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(AttributeConstant.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(AttributeConstant.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
