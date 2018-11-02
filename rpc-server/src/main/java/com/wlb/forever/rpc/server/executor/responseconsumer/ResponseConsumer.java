package com.wlb.forever.rpc.server.executor.responseconsumer;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:17
 * @Description:
 */
public interface ResponseConsumer {
    boolean responseConsumer(Service consumerService, String producerServiceId, List<Service> producerServices, ConsumerServiceResponsePacket consumerServiceResponsePacket);
}
