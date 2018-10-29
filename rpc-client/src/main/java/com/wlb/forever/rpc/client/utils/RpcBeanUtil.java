package com.wlb.forever.rpc.client.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/29 13:36
 * @Description:
 */
public class RpcBeanUtil {
    private static final Map<String, Object> rpcBeanMap = new ConcurrentHashMap<>();

    /**
     * 根据beanName判断RPC调用bean缓存map中是否存在
     *
     * @param beanName
     * @return
     */
    public static boolean hasBean(String beanName) {
        return rpcBeanMap.containsKey(beanName);
    }

    public static void putBean(String beanName, Object bean) {
        rpcBeanMap.put(beanName, bean);
    }

    public static Object getBean(String beanName) {
        if (hasBean(beanName)) {
            return rpcBeanMap.get(beanName);
        }
        return null;
    }

}
