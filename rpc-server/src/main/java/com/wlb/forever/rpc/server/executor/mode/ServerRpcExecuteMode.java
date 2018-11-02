package com.wlb.forever.rpc.server.executor.mode;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;


/**
 * @Auther: william
 * @Date: 18/10/31 14:32
 * @Description:
 */
public interface ServerRpcExecuteMode {
    void requestProducer(ProducerServiceRequestPacket producerServiceRequestPacket);

    boolean responseConsumer(String serviceId, ConsumerServiceResponsePacket consumerServiceResponsePacket);
}
