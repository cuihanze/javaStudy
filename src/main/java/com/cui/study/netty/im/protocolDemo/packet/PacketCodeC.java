package com.cui.study.netty.im.protocolDemo.packet;

import com.cui.study.netty.im.protocolDemo.constants.CommandConstant;
import com.cui.study.netty.im.protocolDemo.constants.SerializerConstant;
import com.cui.study.netty.im.protocolDemo.packet.request.*;
import com.cui.study.netty.im.protocolDemo.packet.response.*;
import com.cui.study.netty.im.protocolDemo.serializer.JsonSerializer;
import com.cui.study.netty.im.protocolDemo.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

// 数据包的编码和解码
public class PacketCodeC {
    // 编码
    public static ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        return encode(byteBuf, packet);
    }

    // 编码
    public static ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 2. 实际编码过程
        byteBuf.writeInt(Packet.getMagicNumber());// 魔数
        byteBuf.writeByte(packet.getVersion());// 版本
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());// 序列化算法
        byteBuf.writeByte(packet.getCommand());// 指令
        byteBuf.writeInt(bytes.length);// 数据长度
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    // 解码
    public static Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列换算法标识
        byte serializerAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();

        // 序列化后的数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserializer(requestType, bytes);
        }

        return null;
    }

    private static Serializer getSerializer(byte serializerAlgorithm) {
        if (serializerAlgorithm == SerializerConstant.JSON) {
            return new JsonSerializer();
        }
        return null;
    }

    private static Class<? extends Packet> getRequestType(byte command) {
        if (command == CommandConstant.LOGIN_REQUEST) {
            return LoginRequestPacket.class;
        } else if (command == CommandConstant.LOGIN_RESPONSE) {
            return LoginResponsePacket.class;
        } else if (command == CommandConstant.MESSAGE_REQUEST) {
            return MessageRequestPacket.class;
        } else if (command == CommandConstant.MESSAGE_RESPONSE) {
            return MessageResponsePacket.class;
        } else if (command == CommandConstant.LOGOUT_REQUEST) {
            return LogoutRequestPacket.class;
        } else if (command == CommandConstant.LOGOUT_RESPONSE) {
            return LogoutResponsePacket.class;
        } else if (command == CommandConstant.GROUP_CREATE_REQUEST) {
            return CreateGroupRequestPacket.class;
        } else if (command == CommandConstant.GROUP_CREATE_RESPONSE) {
            return CreateGroupResponsePacket.class;
        } else if (command == CommandConstant.GROUP_SEND_REQUEST) {
            return SendGroupRequestPacket.class;
        } else if (command == CommandConstant.GROUP_SEND_RESPONSE) {
            return SendGroupResponsePacket.class;
        }

        return null;
    }
}
