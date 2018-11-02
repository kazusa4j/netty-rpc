package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.common.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.cache.BalanceModeCache;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.config.RpcServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

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
    private BalanceModeCache balanceModeCache;

    @Async(value = "threadPoolServerResponse")
    public void executeTask(ChannelHandlerContext ch, Packet packet) {
        ProducerServiceResponsePacket producerServiceResponsePacket = (ProducerServiceResponsePacket) packet;
        ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
        consumerServiceResponsePacket.setRpcResponseInfo(producerServiceResponsePacket.getRpcResponseInfo());
        String requestId = producerServiceResponsePacket.getRpcResponseInfo().getRequestId();
        if (RpcServerConfig.BALANCE_ENABLE) {
            BalanceMode balanceMode = balanceModeCache.getBalanceMode(requestId);
            if (balanceMode != null) {
                Service service = ServiceUtil.getService(ch.channel());
                if (service == null) {
                    log.warn("service为NULL");
                    return;
                }
                if (balanceMode.responseConsumer(service.getServiceId(), consumerServiceResponsePacket)) {
                    balanceModeCache.removeBalanceMode(requestId);
                }
            }
        } else {
            Channel channel = ServiceUtil.getChannel(producerServiceResponsePacket.getRpcResponseInfo().getConsumerService(), producerServiceResponsePacket.getRpcResponseInfo().getConsumerService().getServiceName());
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(consumerServiceResponsePacket);
            }
        }
    }
}
