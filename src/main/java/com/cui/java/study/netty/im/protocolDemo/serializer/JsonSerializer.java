package com.cui.java.study.netty.im.protocolDemo.serializer;

import com.alibaba.fastjson.JSON;
import com.cui.java.study.netty.im.protocolDemo.constants.SerializerConstant;

public class JsonSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerConstant.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
