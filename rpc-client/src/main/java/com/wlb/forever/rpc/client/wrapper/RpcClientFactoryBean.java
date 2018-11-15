package com.wlb.forever.rpc.client.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;



/**
 * @Auther: william
 * @Date: 18/10/23 16:30
 * @Description:
 */
@Slf4j
public class RpcClientFactoryBean<T> implements FactoryBean<T> {
    private Class<T> rpcClientInterface;

    public void setRpcClientInterface(Class<T> rpcClientInterface) {
        this.rpcClientInterface = rpcClientInterface;
    }

    @Override
    public T getObject() throws Exception {
        return getMapper();
    }

    @Override
    public Class<?> getObjectType() {
        return rpcClientInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public <T> T getMapper() {
        final RpcProxyFactory<T> mapperProxyFactory = (RpcProxyFactory<T>) RpcClientsRegistrar.knownRpcClients.get(this.rpcClientInterface);
        if (mapperProxyFactory == null) {
            log.error("Type " + this.rpcClientInterface + "不存在");
        }
        try {
            return mapperProxyFactory.newInstance();
        } catch (Exception e) {

            log.error(this.rpcClientInterface + "实例化出现异常 " + e, e);
        }
        return null;
    }
}
