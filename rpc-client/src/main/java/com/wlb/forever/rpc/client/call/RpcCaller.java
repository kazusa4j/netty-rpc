package com.wlb.forever.rpc.client.call;

import com.wlb.forever.rpc.client.RpcClientStarter;
import com.wlb.forever.rpc.client.exception.RpcConsumerException;
import com.wlb.forever.rpc.client.handler.ConsumerServiceResponseHandler;
import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.utils.RpcSerializerUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @Auther: william
 * @Date: 18/10/19 09:41
 * @Description:发送RPC请求包,并返回请求结果
 */
@Slf4j
public class RpcCaller extends AbstractRpcCaller {

    public RpcCaller() {
    }

    @Override
    public <T> T getResult(ConsumerServiceRequestPacket consumerServiceRequestPacket, Class<T> clazz) {
        if (RpcClientStarter.channel == null || !RpcClientStarter.channel.isActive()) {
            throw new RpcConsumerException("RPC服务器无法连接");
        }
        try {
            ConsumerServiceResponseHandler.messageMap.put(consumerServiceRequestPacket.getRpcRequestInfo().getRequestId(), this);
            RpcClientStarter.channel.writeAndFlush(consumerServiceRequestPacket);
            lock.lock();
            await();
            if (this.response != null) {
                RpcResponseInfo responseInfo = this.response.getRpcResponseInfo();
                Integer code = responseInfo.getCode();
                if (code == 0) {
                    if (responseInfo.getResult() != null) {
                        return RpcSerializerUtil.deserializer(clazz, responseInfo.getResult());
                    } else {
                        return null;
                    }
                } else {
                    throw new RpcConsumerException(responseInfo.getDesc());
                }
            } else {
                throw new RpcConsumerException("RPC调用没有收到返回");
            }
        } finally {
            ConsumerServiceResponseHandler.clearOverRequest(consumerServiceRequestPacket.getRpcRequestInfo().getRequestId());
            lock.unlock();
        }
    }


}
