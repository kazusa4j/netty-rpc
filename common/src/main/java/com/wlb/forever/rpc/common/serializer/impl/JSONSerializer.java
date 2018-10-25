package com.wlb.forever.rpc.common.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.wlb.forever.rpc.common.serializer.Serializer;
import com.wlb.forever.rpc.common.serializer.SerializerAlgorithm;

/**
 * @Auther: william
 * @Date: 18/10/17 16:39
 * @Description:
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
