package com.wlb.forever.rpc.server.balance.responseconsumer.impl;

import com.wlb.forever.rpc.common.constant.RpcResponseCode;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.balance.responseconsumer.ResponseConsumer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:46
 * @Description:
 */
@Slf4j
public class FastestSuccessResponseConsumer implements ResponseConsumer {
    private FastestSuccessResponseConsumer() {

    }

    private static class FastestSuccessResponseConsumerHolder {
        private static final FastestSuccessResponseConsumer INSTANCE = new FastestSuccessResponseConsumer();
    }

    public static FastestSuccessResponseConsumer getInstance() {
        return FastestSuccessResponseConsumerHolder.INSTANCE;
    }


    @Override
    public boolean responseConsumer(String consumerServiceId, String consumerServiceName, String producerServiceId,List<String> serviceList, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        if (!serviceList.contains(producerServiceId)) {
            log.error("返回请求结果，服务生产者CHANNEL不匹配");
            return false;
        }
        if (consumerServiceResponsePacket.getRpcResponseInfo().getCode() != RpcResponseCode.SUCCESS) {
            serviceList.remove(producerServiceId);
            if (serviceList.size() > 0) {
                return false;
            } else {
                return true;
            }
        }
        Channel channel = ServiceUtil.getChannel(consumerServiceId, consumerServiceName);
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(consumerServiceResponsePacket);
        }
        return true;
    }
}
