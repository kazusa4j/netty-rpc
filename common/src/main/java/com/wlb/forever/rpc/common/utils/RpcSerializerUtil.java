package com.wlb.forever.rpc.common.utils;

import com.alibaba.fastjson.JSON;
import com.wlb.forever.rpc.common.config.RpcCommonConfig;

/**
 * @Auther: william
 * @Date: 18/10/31 11:42
 * @Description: RPC序列化工具类
 */
public class RpcSerializerUtil {
    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serializer(Object object) {
        return serializer(object,RpcCommonConfig.ENCODE);
    }

    public static byte[] serializer(Object object,String encode) {
        switch (encode) {
            case "JSON":
                return JSON.toJSONBytes(object);
            case "HESSIAN":
                return HessianUtil.serializer(object);
            default:
                return JSON.toJSONBytes(object);
        }
    }

    /**
     * 反序列化
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return deserializer(clazz, bytes, RpcCommonConfig.ENCODE);
    }

    public static <T> T deserializer(Class<T> clazz, byte[] bytes, String encode) {
        switch (encode) {
            case "JSON":
                return JSON.parseObject(bytes, clazz);
            case "HESSIAN":
                return HessianUtil.deserialize(clazz, bytes);
            default:
                return JSON.parseObject(bytes, clazz);
        }
    }


}
