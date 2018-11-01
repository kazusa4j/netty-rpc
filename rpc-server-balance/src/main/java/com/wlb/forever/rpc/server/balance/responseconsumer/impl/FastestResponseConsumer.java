package com.wlb.forever.rpc.server.balance.responseconsumer.impl;

import com.wlb.forever.rpc.common.constant.RpcResponseCode;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.balance.responseconsumer.ResponseConsumer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

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
    public boolean responseConsumer(String consumerServiceId, String consumerServiceName, String producerServiceId, List<String> serviceList, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        if (!serviceList.contains(producerServiceId)) {
            log.error("返回请求结果，服务生产者CHANNEL不匹配");
            return false;
        }
        Channel channel = ServiceUtil.getChannel(consumerServiceId, consumerServiceName);
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(consumerServiceResponsePacket);
        }
        return true;
    }
}
