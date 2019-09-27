package com.cui.study.netty.im.protocolDemo.packet;

import com.cui.study.netty.im.protocolDemo.enums.CommandEnum;
import com.cui.study.netty.im.protocolDemo.enums.SerializerEnum;
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
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 3. 实际编码过程
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
        if (serializerAlgorithm == SerializerEnum.JSON.getType()) {
            return new JsonSerializer();
        }
        return null;
    }

    private static Class<? extends Packet> getRequestType(byte command) {
        if (command == CommandEnum.LOGIN_REQUEST.getCommand()) {
            return LoginRequestPacket.class;
        } else if (command == CommandEnum.LOGIN_RESPONSE.getCommand()) {
            return LoginResponsePacket.class;
        }

        return null;
    }
}
