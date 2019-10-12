package com.cui.study.netty.im.protocolDemo.constants;

public class CommandConstant {
    public static byte LOGIN_REQUEST = (byte) 1;
    public static byte LOGIN_RESPONSE = (byte) 2;

    public static byte MESSAGE_REQUEST = (byte) 3;
    public static byte MESSAGE_RESPONSE = (byte) 4;

    public static byte GROUP_CREATE_REQUEST = (byte) 5;
    public static byte GROUP_CREATE_RESPONSE = (byte) 6;

    public static byte LOGOUT_REQUEST = (byte) 7;
    public static byte LOGOUT_RESPONSE = (byte) 8;

    public static byte GROUP_SEND_REQUEST = (byte) 9;
    public static byte GROUP_SEND_RESPONSE = (byte) 10;

    public static byte HEARTBEAT_REQUEST = (byte) 11;
    public static byte HEARTBEAT_RESPONSE = (byte) 12;
}
