package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.cache.ExecuteModeCache;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.SERVER_EXCEPTION;

/**
 * @Auther: william
 * @Date: 18/10/26 09:39
 * @Description: 处理生产者RPC调用返回结果
 */
@Component
@Slf4j
public class ProducerResponseExecutor {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolServerResponse;//变量名称为定义的线程池bean定义的name属性名。
    @Autowired
    private ExecuteModeCache executeModeCache;

    @Async(value = "threadPoolServerResponse")
    public void executeTask(ChannelHandlerContext ch, AbstractPacket abstractPacket) {
        ProducerServiceResponsePacket producerServiceResponsePacket = (ProducerServiceResponsePacket) abstractPacket;
        try {
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            consumerServiceResponsePacket.setRpcResponseInfo(producerServiceResponsePacket.getRpcResponseInfo());
            String requestId = producerServiceResponsePacket.getRpcResponseInfo().getRequestId();
            ServerRpcExecuteMode serverRpcExecuteMode = executeModeCache.getBalanceMode(requestId);
            if (serverRpcExecuteMode != null) {
                Service service = ServiceUtil.getService(ch.channel());
                if (service == null) {
                    log.warn("service为NULL");
                    return;
                }
                if (serverRpcExecuteMode.responseConsumer(service.getServiceId(), consumerServiceResponsePacket)) {
                    executeModeCache.removeBalanceMode(requestId);
                }
            }
        } catch (Exception e) {
            log.warn("({})RPC调用处理生产者响应发生异常", producerServiceResponsePacket.getRpcResponseInfo().getConsumerService().getServiceName());
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
            rpcResponseInfo.setRequestId(producerServiceResponsePacket.getRpcResponseInfo().getRequestId());
            rpcResponseInfo.setCode(SERVER_EXCEPTION);
            rpcResponseInfo.setDesc("RPC调用服务器发生异常");
            rpcResponseInfo.setResult(null);
            consumerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
            ch.writeAndFlush(consumerServiceResponsePacket);
        }

    }
}
