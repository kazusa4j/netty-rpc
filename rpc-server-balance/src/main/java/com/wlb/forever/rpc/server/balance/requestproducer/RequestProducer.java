package com.wlb.forever.rpc.server.balance.requestproducer;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 14:09
 * @Description:
 */
public interface RequestProducer {
    void requestProducer(List<String> serviceList, ProducerServiceRequestPacket producerServiceRequestPacket);
}
