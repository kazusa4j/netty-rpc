package com.wlb.forever.rpc.server.executor.requestproducer.impl;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.executor.requestproducer.RequestProducer;
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
    public void requestProducer(List<Service> pruducerServices, ProducerServiceRequestPacket producerServiceRequestPacket) {
        String toServiceName = producerServiceRequestPacket.getRpcRequestInfo().getProducerServiceName();
        pruducerServices.forEach(service -> {
            Channel channel = ServiceUtil.getChannel(service, toServiceName);
            channel.writeAndFlush(producerServiceRequestPacket);
        });
    }
}
