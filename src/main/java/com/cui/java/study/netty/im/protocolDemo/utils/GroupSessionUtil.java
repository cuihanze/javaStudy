package com.cui.java.study.netty.im.protocolDemo.utils;

import com.cui.java.study.netty.im.protocolDemo.constants.AttributeConstant;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupSessionUtil {
    private static final Map<String, List<String>> groupMap = new ConcurrentHashMap<>();

    public static String createGroup(Channel channel, List<String> userIds) {
        String groupId = String.valueOf(userIds.hashCode());
        groupMap.put(groupId, userIds);
        List<String> groups = getGroups(channel);
        groups.add(groupId);
        channel.attr(AttributeConstant.GROUP).set(groups);
        return groupId;
    }

    public static boolean dissolveGroup(Channel channel, String groupId) {
        if (groupMap.get(groupId) != null) {
            groupMap.remove(groupId);

            return removeChannelGroup(channel, groupId);
        } else {
            return false;
        }
    }

    private static boolean removeChannelGroup(Channel channel, String groupId) {
        List<String> groups = getGroups(channel);
        boolean result = groups.remove(groupId);
        channel.attr(AttributeConstant.GROUP).set(groups);
        return result;
    }

    public static boolean exitGroup(Channel channel, String groupId) {
        if (groupMap.get(groupId) == null) {
            return false;
        }
        List<String> userIds = groupMap.get(groupId);
        boolean result = userIds.remove(SessionUtil.getSession(channel).getUserId());

        return result && removeChannelGroup(channel, groupId);
    }

    public static boolean joinGroup(Channel channel, String groupId) {
        if (groupMap.get(groupId) == null) {
            return false;
        }
        List<String> userIds = groupMap.get(groupId);
        userIds.add(SessionUtil.getSession(channel).getUserId());
        return addGroup(channel, groupId);
    }

    private static boolean addGroup(Channel channel, String groupId) {
        List<String> groups = getGroups(channel);
        groups.add(groupId);
        channel.attr(AttributeConstant.GROUP).set(groups);
        return true;
    }

    public static List<String> getGroups(Channel channel) {
        List<String> groups;
        if (channel.attr(AttributeConstant.GROUP).get() == null) {
            groups = new ArrayList<>();
        } else {
            groups = channel.attr(AttributeConstant.GROUP).get();
        }
        return groups;
    }

    public static List<String> getUserIds(String groupId) {
        return groupMap.get(groupId);
    }

}
