package com.wlb.forever.rpc.server.executor.requestproducer;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:09
 * @Description:
 */
public interface RequestProducer {
    void requestProducer(List<Service> producerServices, ProducerServiceRequestPacket producerServiceRequestPacket);
}
