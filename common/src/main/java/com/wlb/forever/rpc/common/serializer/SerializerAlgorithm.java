package com.wlb.forever.rpc.common.serializer;

/**
 * @Auther: william
 * @Date: 18/10/17 16:38
 * @Description:序列化算法
 */
public interface SerializerAlgorithm {
    /**
     * json 序列化
     */
    byte JSON = 1;

    /**
     *hessian 序列化
     */
    byte HESSIAN = 2;
}
