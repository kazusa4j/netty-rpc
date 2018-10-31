package com.wlb.forever.rpc.client.wrapper;

import com.wlb.forever.rpc.client.RpcClientStarter;
import com.wlb.forever.rpc.client.call.RpcCaller;
import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.utils.RpcSerializerUtil;
import com.wlb.forever.rpc.common.utils.UniqueIdUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Auther: william
 * @Date: 18/10/23 14:28
 * @Description: RPC调用执行方法
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
     *
     * @param args
     * @return
     */
    public Object execute(Object[] args) {
        Object result;
        RpcCaller rpcServiceCaller = new RpcCaller();
        ConsumerServiceRequestPacket consumerServiceRequestPacket = new ConsumerServiceRequestPacket();
        RpcRequestInfo rpcRequestInfo = new RpcRequestInfo();
        String requestId = UniqueIdUtil.getUUID();
        rpcRequestInfo.setRequestId(requestId);
        rpcRequestInfo.setToServiceName(serviceName);
        rpcRequestInfo.setFromServiceId(RpcClientStarter.SERVICE_ID);
        rpcRequestInfo.setFromServiceName(RpcClientStarter.SERVICE_NAME);
        rpcRequestInfo.setBeanName(beanName);
        rpcRequestInfo.setMethodName(method.methodName);
        rpcRequestInfo.setParamTypes(getParamTypeNames(method.paramTypes));
        rpcRequestInfo.setParams(argsToByte(args));
        consumerServiceRequestPacket.setRpcRequestInfo(rpcRequestInfo);
        result = rpcServiceCaller.getResult(consumerServiceRequestPacket, method.returnType);
        return result;
    }

    /**
     *
     * @param classes
     * @return
     */
    private String[] getParamTypeNames(Class[] classes) {
        if (classes == null) {
            return new String[0];
        }
        String[] paramTypeNames = new String[classes.length];
        int i = 0;
        for (Class classz : classes) {
            paramTypeNames[i] = classz.getCanonicalName();
            i++;
        }
        return paramTypeNames;
    }

    private byte[][] argsToByte(Object[] args) {
        if (args == null) {
            return new byte[0][];
        }
        byte[][] params = new byte[args.length][];
        int i = 0;
        for (Object obj : args) {
            params[i] = RpcSerializerUtil.serializer(obj);
            i++;
        }
        return params;
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
