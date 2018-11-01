package com.wlb.forever.rpc.server.balance.responseconsumer;

import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:17
 * @Description:
 */
public interface ResponseConsumer {
    boolean responseConsumer(String consumerServiceId, String consumerServiceName, String producerServiceId, List<String> serviceList, ConsumerServiceResponsePacket consumerServiceResponsePacket);
}
