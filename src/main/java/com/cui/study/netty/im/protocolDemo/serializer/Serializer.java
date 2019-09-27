package com.cui.study.netty.im.protocolDemo;

public interface Serializer {
    // 序列化算法
    byte getSerializerAlgorithm();

    // 序列化
    byte[] serialize(Object object);

    // 反序列化
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
