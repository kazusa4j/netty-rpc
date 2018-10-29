package com.wlb.forever.rpc.client.call;

import com.alibaba.fastjson.JSON;
import com.wlb.forever.rpc.client.RpcClient;
import com.wlb.forever.rpc.client.exception.RpcCallClientException;
import com.wlb.forever.rpc.client.handler.ClientServiceResponseHandler;
import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import lombok.extern.slf4j.Slf4j;


/**
 * @Auther: william
 * @Date: 18/10/19 09:41
 * @Description:
 */
@Slf4j
public class RpcJsonCaller extends AbstractRpcCaller {

    public RpcJsonCaller() {
    }

    @Override
    public <T> T getResult(ClientServiceRequestPacket clientServiceRequestPacket, Class<T> clazz) {
        if (RpcClient.channel == null || !RpcClient.channel.isActive()) {
            throw new RpcCallClientException("RPC服务器无法连接");
        }
        try {
            ClientServiceResponseHandler.messageMap.put(clientServiceRequestPacket.getRequestId(), this);
            RpcClient.channel.writeAndFlush(clientServiceRequestPacket);
            //log.info("发送RPC请求");
            lock.lock();
            await();
            if (this.response != null) {
                Integer code = this.response.getCode();
                if (code == 0) {
                    if (this.response.getResult() != null) {
                        return JSON.parseObject(this.response.getResult().toString(), clazz);
                    } else {
                        return null;
                    }
                } else {
                    throw new RpcCallClientException(this.response.getDesc());
                }
            } else {
                throw new RpcCallClientException("RPC调用没有收到返回");
            }
        } finally {
            ClientServiceResponseHandler.clearOverRequest(clientServiceRequestPacket.getRequestId());
            lock.unlock();
        }
    }


}
