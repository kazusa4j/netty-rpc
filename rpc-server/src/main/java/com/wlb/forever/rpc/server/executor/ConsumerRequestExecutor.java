package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.cache.BalanceModeCache;
import com.wlb.forever.rpc.server.config.RpcServerConfig;
import com.wlb.forever.rpc.server.utils.BalanceUtil;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import io.netty.channel.Channel;
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
    private BalanceModeCache balanceModeCache;

    @Async(value = "threadPoolClientRequest")
    public void executeTask(ChannelHandlerContext ch, Packet packet) {
        ConsumerServiceRequestPacket consumerServiceRequestPacket = (ConsumerServiceRequestPacket) packet;
        List<String> serviceIds = ServiceUtil.getChannelsServiceId(consumerServiceRequestPacket.getRpcRequestInfo().getToServiceName());
        if (serviceIds == null || serviceIds.size() <= 0) {
            log.warn("RPC服务({})不存在", consumerServiceRequestPacket.getRpcRequestInfo().getToServiceName());
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
            rpcResponseInfo.setRequestId(consumerServiceRequestPacket.getRpcRequestInfo().getRequestId());
            rpcResponseInfo.setCode(NO_SERVICE);
            rpcResponseInfo.setDesc("(" + consumerServiceRequestPacket.getRpcRequestInfo().getToServiceName() + ")RPC服务不存在");
            rpcResponseInfo.setResult(null);
            consumerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
            ch.writeAndFlush(consumerServiceResponsePacket);
        } else {
            ProducerServiceRequestPacket producerServiceRequestPacket = new ProducerServiceRequestPacket();
            producerServiceRequestPacket.setRpcRequestInfo(consumerServiceRequestPacket.getRpcRequestInfo());
            BalanceMode balanceMode = BalanceUtil.getBalanceMode(RpcServerConfig.BALANCE_MODE, ch.channel(), serviceIds);
            if (balanceMode == null) {
                List<Channel> channels = ServiceUtil.getChannels(consumerServiceRequestPacket.getRpcRequestInfo().getToServiceName());
                channels.forEach(channel -> channel.writeAndFlush(producerServiceRequestPacket));
            } else {
                balanceMode.requestProducer(producerServiceRequestPacket);
                balanceModeCache.put(producerServiceRequestPacket.getRpcRequestInfo().getRequestId(), balanceMode);
            }
        }
    }
}