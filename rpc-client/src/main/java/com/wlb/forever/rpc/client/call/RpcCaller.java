package com.wlb.forever.rpc.client.call;

import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;

/**
 * @Auther: william
 * @Date: 18/10/19 17:30
 * @Description:
 */
public interface RpcCaller {


    <T> T getResult(ConsumerServiceRequestPacket consumerServiceRequestPacket, Class<T> clazz);
}
