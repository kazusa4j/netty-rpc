package com.wlb.forever.rpc.client.wrapper;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: william
 * @Date: 18/10/23 14:29
 * @Description:
 */
@Slf4j
public class RpcClientProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -1093632498314308732L;
    private final Class<T> rpcClientInterface;
    private final Map<Method, RpcClientMethod> methodCache;
    private final String serviceName;
    private final String beanName;

    public RpcClientProxy(String serviceName, String beanName, Class<T> rpcClientInterface, Map<Method, RpcClientMethod> methodCache) {
        this.serviceName = serviceName;
        this.beanName = beanName;
        this.rpcClientInterface = rpcClientInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                log.error("RpcClientProxy代理类invoke失败");
            }
        }
        RpcClientMethod mapperMethod = cachedMapperMethod(this.serviceName, this.beanName, method);
        return mapperMethod.execute(args);
    }

    private RpcClientMethod cachedMapperMethod(String serviceName, String beanName, Method method) {
        RpcClientMethod mapperMethod = this.methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new RpcClientMethod(serviceName, beanName, method);
            this.methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}
