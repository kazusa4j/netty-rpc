package com.wlb.forever.rpc.common.serializer;



/**
 * @Auther: william
 * @Date: 18/10/17 16:38
 * @Description:序列化
 */
public interface Serializer {

    /**
     * 序列化算法
     *
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
