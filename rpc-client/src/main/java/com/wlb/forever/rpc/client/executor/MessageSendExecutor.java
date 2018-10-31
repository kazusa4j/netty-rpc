package com.wlb.forever.rpc.client.executor;

import com.wlb.forever.rpc.client.exception.RpcProducerException;
import com.wlb.forever.rpc.client.utils.RpcBeanUtil;
import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.RpcSerializerUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.*;

/**
 * @Auther: william
 * @Date: 18/10/22 10:13
 * @Description: 线程池处理RPC调用请求
 */
@Component
@Slf4j
public class MessageSendExecutor {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolRpc;//变量名称为定义的线程池bean定义的name属性名。


    /**
     * 发送RPC服务请求结果
     *
     * @param producerServiceRequestPacket
     * @param channelHandlerContext
     */
    @Async(value = "threadPoolRpc")
    public void send(ProducerServiceRequestPacket producerServiceRequestPacket, ChannelHandlerContext channelHandlerContext) {
        //checkAvtiveThreadNum();
        RpcRequestInfo rpcRequestInfo = producerServiceRequestPacket.getRpcRequestInfo();
        String encode = rpcRequestInfo.getEncode();
        String requestId = rpcRequestInfo.getRequestId();
        String fromServiceId = rpcRequestInfo.getFromServiceId();
        String fromServiceName = rpcRequestInfo.getFromServiceName();
        String beanName = rpcRequestInfo.getBeanName();
        String methodName = rpcRequestInfo.getMethodName();
        String[] classzz = rpcRequestInfo.getParamTypes();
        byte[][] params = rpcRequestInfo.getParams();
        ProducerServiceResponsePacket producerServiceResponsePacket = assemRpcResponsePacket(encode, requestId, fromServiceId, fromServiceName, beanName, methodName, classzz, params);
        channelHandlerContext.writeAndFlush(producerServiceResponsePacket);
        log.info("返回{}RPC调用服务结果", fromServiceName);
    }

    /**
     * 获取参数类型
     *
     * @param paramTypeNames
     * @return
     * @throws Exception
     */
    private Class[] getParamTypes(String[] paramTypeNames) throws Exception {
        Class[] paramTypes = new Class[paramTypeNames.length];
        int i = 0;
        for (String paramTypeName : paramTypeNames) {
            paramTypes[i] = Class.forName(paramTypeName);
            i++;
        }
        return paramTypes;
    }

    private Object[] getArgs(Class[] classes, byte[][] params, String encode) {
        if (classes.length != params.length) {
            throw new RpcProducerException("参数类型与参数量不符");
        }
        Object[] args = new Object[classes.length];
        int i = 0;
        for (byte[] param : params) {
            args[i] = RpcSerializerUtil.deserializer(classes[i], param, encode);
        }
        return args;
    }

    /**
     * 组装RPC调用返回包
     *
     * @param requestId
     * @param fromServiceId
     * @param fromServiceName
     * @param beanName
     * @param methodName
     * @param classNames
     * @param params
     * @return
     */
    private ProducerServiceResponsePacket assemRpcResponsePacket(String encode, String requestId, String fromServiceId, String fromServiceName, String beanName, String methodName, String[] classNames, byte[][] params) {
        ProducerServiceResponsePacket producerServiceResponsePacket = new ProducerServiceResponsePacket();
        StringBuilder desc = new StringBuilder();
        Integer code = SUCCESS;
        RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
        rpcResponseInfo.setFromServiceId(fromServiceId);
        rpcResponseInfo.setFromServiceName(fromServiceName);
        rpcResponseInfo.setRequestId(requestId);
        try {
            Class[] classzz = getParamTypes(classNames);
            Object bean = RpcBeanUtil.getRpcBean(beanName, methodName, classzz);
            if (bean != null) {
                Method mh = ReflectionUtils.findMethod(bean.getClass(), methodName, classzz);
                if (mh == null) {
                    code = NO_METHOD;
                    setNoMethodDesc(desc, beanName, methodName, classNames);
                } else {

                    Object[] args = getArgs(classzz, params, encode);
                    Object result = ReflectionUtils.invokeMethod(mh, bean, args);
                    if (result != null) {
                        rpcResponseInfo.setResult(RpcSerializerUtil.serializer(result,encode));
                    }
                    setSuccessDesc(desc, beanName, methodName, classNames);
                    rpcResponseInfo.setCode(code);
                    rpcResponseInfo.setDesc(desc.toString());
                    producerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
                    log.info(desc.toString());
                    return producerServiceResponsePacket;

                }
            } else {
                code = NO_BEAN;
                setNoBeanDesc(desc, beanName);
            }
        } catch (Exception e) {
            code = SERVICE_EXCEPTION;
            setExceptionDesc(desc, beanName, methodName, classNames);
        }
        log.error(desc.toString());
        rpcResponseInfo.setCode(code);
        rpcResponseInfo.setDesc(desc.toString());
        producerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
        return producerServiceResponsePacket;
    }

    /**
     * 设置RPC调用成功描述
     *
     * @param desc
     * @param beanName
     * @param methodName
     * @param classzz
     */
    private void setSuccessDesc(StringBuilder desc, String beanName, String methodName, String[] classzz) {
        desc.append("RPC调用");
        desc.append(beanName);
        desc.append(".");
        desc.append(methodName);
        desc.append("(");
        setParamTypes(desc, classzz);
        desc.append(")");
        desc.append("成功");
    }

    /**
     * bean不存在描述
     *
     * @param desc
     * @param beanName
     */
    private void setNoBeanDesc(StringBuilder desc, String beanName) {
        desc.append("RPC调用无法获取bean:");
        desc.append(beanName);
    }

    /**
     * 方法不存在描述
     *
     * @param desc
     * @param beanName
     * @param methodName
     * @param classzz
     */
    private void setNoMethodDesc(StringBuilder desc, String beanName, String methodName, String[] classzz) {
        desc.append("RPC调用bean:");
        desc.append(beanName);
        desc.append("不存在方法:");
        desc.append(methodName);
        desc.append("(");
        setParamTypes(desc, classzz);
        desc.append(")");
    }

    /**
     * 反射调用方法异常描述
     *
     * @param desc
     * @param beanName
     * @param methodName
     * @param classzz
     */
    private void setExceptionDesc(StringBuilder desc, String beanName, String methodName, String[] classzz) {
        desc.append("RPC调用");
        desc.append(beanName);
        desc.append(".");
        desc.append(methodName);
        desc.append("(");
        setParamTypes(desc, classzz);
        desc.append(")");
        desc.append("发生异常");
    }

    /**
     * 设置方法参数
     *
     * @param desc
     * @param classzz
     */
    private void setParamTypes(StringBuilder desc, String[] classzz) {
        if (classzz.length > 0) {
            for (String classz : classzz) {
                desc.append(classz);
                desc.append(",");
            }
            desc.deleteCharAt(desc.length() - 1);
        }
    }

    /**
     * 检查活跃线程数
     */
    private int checkAvtiveThreadNum() {
        int num = threadPoolRpc.getActiveCount();
        log.info("线程数:" + num);
        return num;
    }

}
