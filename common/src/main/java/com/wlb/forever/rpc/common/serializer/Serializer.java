package com.wlb.forever.rpc.common.serializer;


import com.wlb.forever.rpc.common.serializer.impl.JSONSerializer;

/**
 * @Auther: william
 * @Date: 18/10/17 16:38
 * @Description:
 */
public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
