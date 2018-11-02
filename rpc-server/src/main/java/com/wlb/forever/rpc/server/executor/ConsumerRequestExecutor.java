package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.server.executor.factory.ServerExecuteModeFactory;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.cache.ExecuteModeCache;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.NO_SERVICE;

/**
 * @Auther: william
 * @Date: 18/10/26 09:38
 * @Description: 处理消费者RPC调用
 */
@Component
@Slf4j
public class ConsumerRequestExecutor {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolClientRequest;//变量名称为定义的线程池bean定义的name属性名。

    @Autowired
    private ExecuteModeCache executeModeCache;

    @Autowired
    private ServerExecuteModeFactory serverExecuteModeFactory;

    @Async(value = "threadPoolClientRequest")
    public void executeTask(ChannelHandlerContext ch, Packet packet) {
        ConsumerServiceRequestPacket consumerServiceRequestPacket = (ConsumerServiceRequestPacket) packet;
        List<Service> producerServices = ServiceUtil.getChannelsServiceId(consumerServiceRequestPacket.getRpcRequestInfo().getProducerServiceName());
        if (producerServices == null || producerServices.size() <= 0) {
            log.warn("RPC服务({})不存在", consumerServiceRequestPacket.getRpcRequestInfo().getProducerServiceName());
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
            rpcResponseInfo.setRequestId(consumerServiceRequestPacket.getRpcRequestInfo().getRequestId());
            rpcResponseInfo.setCode(NO_SERVICE);
            rpcResponseInfo.setDesc("(" + consumerServiceRequestPacket.getRpcRequestInfo().getProducerServiceName() + ")RPC服务不存在");
            rpcResponseInfo.setResult(null);
            consumerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
            ch.writeAndFlush(consumerServiceResponsePacket);
        } else {
            ProducerServiceRequestPacket producerServiceRequestPacket = new ProducerServiceRequestPacket();
            RpcRequestInfo rpcRequestInfo = consumerServiceRequestPacket.getRpcRequestInfo();
            producerServiceRequestPacket.setRpcRequestInfo(rpcRequestInfo);

            ServerRpcExecuteMode serverRpcExecuteMode = serverExecuteModeFactory.getExecuteMode(ServiceUtil.getService(ch.channel()), producerServices);
            serverRpcExecuteMode.requestProducer(producerServiceRequestPacket);
            executeModeCache.put(producerServiceRequestPacket.getRpcRequestInfo().getRequestId(), serverRpcExecuteMode);
        }
    }


}
