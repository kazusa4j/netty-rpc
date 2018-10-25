package com.wlb.forever.rpc.common.serializer.impl;


import com.wlb.forever.rpc.common.serializer.Serializer;
import com.wlb.forever.rpc.common.serializer.SerializerAlgorithm;

/**
 * @Auther: william
 * @Date: 18/10/17 17:12
 * @Description:
 */
public class JavaSerilizer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JAVA;
    }

    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
