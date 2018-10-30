package com.wlb.forever.rpc.client.executor;

import com.wlb.forever.rpc.client.utils.RpcBeanUtil;
import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.HessianUtil;
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
 * @Description:
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
        String requestId = rpcRequestInfo.getRequestId();
        String fromServiceId = rpcRequestInfo.getFromServiceId();
        String fromServiceName = rpcRequestInfo.getFromServiceName();
        String beanName = rpcRequestInfo.getBeanName();
        String methodName = rpcRequestInfo.getMethodName();
        Class[] classzz = rpcRequestInfo.getParamTypes();
        Object[] params = rpcRequestInfo.getParams();
        ProducerServiceResponsePacket producerServiceResponsePacket = assemRpcResponsePacket(requestId, fromServiceId, fromServiceName, beanName, methodName, classzz, params);
        channelHandlerContext.writeAndFlush(producerServiceResponsePacket);
        log.info("返回{}RPC调用服务结果", fromServiceName);
    }

    /**
     * 组装RPC调用返回包
     *
     * @param requestId
     * @param fromServiceName
     * @param beanName
     * @param methodName
     * @param classzz
     * @param params
     * @return
     */
    private ProducerServiceResponsePacket assemRpcResponsePacket(String requestId, String fromServiceId, String fromServiceName, String beanName, String methodName, Class[] classzz, Object[] params) {
        ProducerServiceResponsePacket producerServiceResponsePacket = new ProducerServiceResponsePacket();
        RpcResponseInfo rpcResponseInfo=new RpcResponseInfo();
        rpcResponseInfo.setFromServiceId(fromServiceId);
        rpcResponseInfo.setFromServiceName(fromServiceName);
        rpcResponseInfo.setRequestId(requestId);
        Object bean = RpcBeanUtil.getRpcBean(beanName, methodName, classzz);

        StringBuilder desc = new StringBuilder();
        Integer code = SUCCESS;
        if (bean != null) {
            Method mh = ReflectionUtils.findMethod(bean.getClass(), methodName, classzz);
            if (mh == null) {
                code = NO_METHOD;
                setNoMethodDesc(desc, beanName, methodName, classzz);
            } else {
                try {
                    Object result = ReflectionUtils.invokeMethod(mh, bean, params);
                    setSuccessDesc(desc, beanName, methodName, classzz);
                    rpcResponseInfo.setCode(code);
                    rpcResponseInfo.setDesc(desc.toString());
                    rpcResponseInfo.setResult(HessianUtil.serializer(result));
                    log.info(desc.toString());
                    producerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
                    return producerServiceResponsePacket;
                } catch (Exception e) {
                    code = SERVICE_EXCEPTION;
                    setExceptionDesc(desc, beanName, methodName, classzz);
                }
            }
        } else {
            code = NO_BEAN;
            setNoBeanDesc(desc, beanName);
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
    private void setSuccessDesc(StringBuilder desc, String beanName, String methodName, Class[] classzz) {
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
    private void setNoMethodDesc(StringBuilder desc, String beanName, String methodName, Class[] classzz) {
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
    private void setExceptionDesc(StringBuilder desc, String beanName, String methodName, Class[] classzz) {
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
    private void setParamTypes(StringBuilder desc, Class[] classzz) {
        if (classzz.length > 0) {
            for (Class classz : classzz) {
                desc.append(classz.getSimpleName());
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
