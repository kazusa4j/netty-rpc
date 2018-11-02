package com.wlb.forever.rpc.server.balance.responseconsumer.impl;

import com.wlb.forever.rpc.common.constant.RpcResponseCode;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.balance.responseconsumer.ResponseConsumer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:45
 * @Description:
 */
@Slf4j
public class FastestResponseConsumer implements ResponseConsumer {

    private FastestResponseConsumer() {

    }

    private static class FastestResponseConsumerHolder {
        private static final FastestResponseConsumer INSTANCE = new FastestResponseConsumer();
    }

    public static FastestResponseConsumer getInstance() {
        return FastestResponseConsumerHolder.INSTANCE;
    }

    @Override
    public boolean responseConsumer(Service consumerService, String producerServiceId, List<Service> producerServices, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        List<String> serviceList = ServiceUtil.getServiceIdsByServices(producerServices);
        if (!serviceList.contains(producerServiceId)) {
            log.error("返回请求结果，服务生产者CHANNEL不匹配");
            return false;
        }
        Channel channel = ServiceUtil.getChannel(consumerService, consumerService.getServiceName());
        if (channel != null && channel.isActive()) {

            channel.writeAndFlush(consumerServiceResponsePacket);
        }else{
            log.error("返回请求结果，活期服务消费者CHANNEL为NULL");
        }

        return true;
    }
}
