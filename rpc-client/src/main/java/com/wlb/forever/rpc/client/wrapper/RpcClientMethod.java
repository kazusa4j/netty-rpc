package com.wlb.forever.rpc.client.wrapper;

import com.wlb.forever.rpc.client.RpcClient;
import com.wlb.forever.rpc.client.call.RpcJsonCaller;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.utils.UniqueIdUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Auther: william
 * @Date: 18/10/23 14:28
 * @Description:
 */
@Slf4j
public class RpcClientMethod {

    private final MethodSignature method;
    private final String serviceName;
    private final String beanName;


    public RpcClientMethod(String serviceName, String beanName, Method method) {
        this.serviceName = serviceName;
        this.beanName = beanName;
        this.method = new MethodSignature(method);
    }

    /**
     * 向服务器发送RPC请求包并返回结果
     * @param args
     * @return
     */
    public Object execute(Object[] args) {
        Object result;
        RpcJsonCaller rpcServiceCaller = new RpcJsonCaller();
        ConsumerServiceRequestPacket consumerServiceRequestPacket = new ConsumerServiceRequestPacket();
        String requestId = UniqueIdUtil.getUUID();
        consumerServiceRequestPacket.setRequestId(requestId);
        consumerServiceRequestPacket.setToServiceName(serviceName);
        consumerServiceRequestPacket.setFromServiceId(RpcClient.SERVICE_ID);
        consumerServiceRequestPacket.setFromServiceName(RpcClient.SERVICE_NAME);
        consumerServiceRequestPacket.setBeanName(beanName);
        consumerServiceRequestPacket.setMethodName(method.methodName);
        consumerServiceRequestPacket.setParamTypes(method.paramTypes);
        consumerServiceRequestPacket.setParams(args);
        result = rpcServiceCaller.getResult(consumerServiceRequestPacket, method.returnType);
        return result;
    }

    public static class MethodSignature {
        private final String methodName;
        private final Class<?> returnType;
        private final Class[] paramTypes;

        public MethodSignature(Method method) {
            this.methodName = method.getName();
            this.returnType = method.getReturnType();
            this.paramTypes = method.getParameterTypes();
        }

    }
}
