package com.wlb.forever.rpc.client.wrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/23 14:25
 * @Description:
 */
public class RpcProxyFactory<T> {
    private final String serviceName;
    private final String beanName;
    private final Class<T> rpcClientInterface;
    private Map<Method, RpcClientMethod> methodCache = new ConcurrentHashMap();

    public RpcProxyFactory(String serviceName, String beanName, Class<T> rpcClientInterface) {
        this.serviceName = serviceName;
        this.beanName = beanName;
        this.rpcClientInterface = rpcClientInterface;
    }

    public Class<T> getRpcClientInterface() {
        return this.rpcClientInterface;
    }

    public Map<Method, RpcClientMethod> getMethodCache() {
        return this.methodCache;
    }

    protected T newInstance(RpcClientProxy<T> rpcClientProxy) {
        return (T) Proxy.newProxyInstance(this.rpcClientInterface.getClassLoader(), new Class[]{this.rpcClientInterface}, rpcClientProxy);
    }

    public T newInstance() {
        RpcClientProxy rpcClientProxy = new RpcClientProxy(this.serviceName, this.beanName, this.rpcClientInterface, this.methodCache);
        return (T) newInstance(rpcClientProxy);
    }
}
