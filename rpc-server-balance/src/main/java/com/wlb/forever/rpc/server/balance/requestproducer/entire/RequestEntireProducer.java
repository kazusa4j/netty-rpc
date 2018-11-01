package com.wlb.forever.rpc.server.balance.requestproducer.entire;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.balance.requestproducer.RequestProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.impl.FastestResponseConsumer;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:38
 * @Description:
 */
public class RequestEntireProducer implements RequestProducer {
    private RequestEntireProducer() {

    }

    private static class RequestEntireProducerHolder {
        private static final RequestEntireProducer INSTANCE = new RequestEntireProducer();
    }

    public static RequestEntireProducer getInstance() {
        return RequestEntireProducerHolder.INSTANCE;
    }

    @Override
    public void requestProducer(List<String> serviceList, ProducerServiceRequestPacket producerServiceRequestPacket) {
        String toServiceName = producerServiceRequestPacket.getRpcRequestInfo().getToServiceName();
        serviceList.forEach(serviceId -> {
            Channel channel = ServiceUtil.getChannel(serviceId, toServiceName);
            channel.writeAndFlush(producerServiceRequestPacket);
        });
    }
}
