package com.wlb.forever.rpc.common.serializer.impl;

import com.wlb.forever.rpc.common.serializer.Serializer;
import com.wlb.forever.rpc.common.serializer.SerializerAlgorithm;
import com.wlb.forever.rpc.common.utils.HessianUtil;


/**
 * @Auther: william
 * @Date: 18/10/30 10:32
 * @Description:HESSIAN序列化
 */
public class HessianSerilizer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.HESSIAN;
    }

    @Override
    public byte[] serialize(Object obj) {
        return HessianUtil.serializer(obj);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return HessianUtil.deserialize(clazz, bytes);
    }
}
