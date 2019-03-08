package com.wlb.forever.rpc.client.utils;

import com.wlb.forever.rpc.common.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/29 13:36
 * @Description: RPC 请求映射bean工具类
 */
@Slf4j
public class RpcBeanUtil {
    private static final Map<String, Object> RPC_BEAN_MAP = new ConcurrentHashMap<>();

    /**
     * 根据beanName判断RPC调用bean缓存map中是否存在
     *
     * @param beanName
     * @return
     */
    public static boolean hasBean(String beanName) {
        return RPC_BEAN_MAP.containsKey(beanName);
    }

    public static void putBean(String beanName, Object bean) {
        RPC_BEAN_MAP.put(beanName, bean);
    }

    public static Object getBean(String beanName) {
        if (hasBean(beanName)) {
            return RPC_BEAN_MAP.get(beanName);
        }
        return null;
    }

    /**
     * 获取RPC调用bean
     *
     * @param name
     * @param methodName
     * @param classzz
     * @return
     */
    public static Object getRpcBean(String name, String methodName, Class[] classzz) {
        Object bean = null;
        if (!hasBean(name)) {
            try {
                bean = SpringBeanUtil.getBean(name);
            } catch (Exception e1) {
                try {
                    String nameLower = name.toLowerCase();
                    String[] beans = SpringBeanUtil.getBeanNames();
                    for (String beanName : beans) {
                        String nameTmp = beanName.toLowerCase();
                        if (nameTmp.indexOf(nameLower) > -1) {
                            Method mh = ReflectionUtils.findMethod(SpringBeanUtil.getBean(beanName).getClass(), methodName, classzz);
                            if (mh != null) {
                                bean = SpringBeanUtil.getBean(beanName);
                                putBean(name, bean);
                                return bean;
                            }
                        }
                    }
                } catch (Exception e2) {
                    log.warn("RPC调用bean({})映射失败", name);
                }
            }
        } else {
            bean = getBean(name);
        }
        return bean;
    }
}
