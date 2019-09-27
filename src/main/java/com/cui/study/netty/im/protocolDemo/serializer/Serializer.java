package com.cui.study.netty.im.protocolDemo.serializer;

public interface Serializer {
    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JsonSerializer();

    // 序列化算法
    byte getSerializerAlgorithm();

    // 序列化
    byte[] serialize(Object object);

    // 反序列化
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
