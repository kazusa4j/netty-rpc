package com.wlb.forever.rpc.client.call;

import com.wlb.forever.rpc.client.exception.RpcCallClientException;
import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: william
 * @Date: 18/10/19 17:17
 * @Description:
 */
@Slf4j
public abstract class AbstractRpcCaller implements RpcCaller {
    protected Lock lock = new ReentrantLock();
    protected Condition finish = lock.newCondition();
    protected ClientServiceResponsePacket response;

    public void over(ClientServiceResponsePacket reponse) {
        try {
            lock.lock();
            finish.signal();
            this.response = reponse;
        } finally {
            lock.unlock();
        }
    }

    protected void await() {
        boolean timeout = false;
        try {
            timeout = finish.await(30 * 1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("RPC请求发生中断故障异常");
            throw new RpcCallClientException("RPC请求发生中断故障异常");
        }
        if (!timeout) {
            log.error("RPC请求超时");
            throw new RpcCallClientException("RPC请求超时");
        }
    }

    public abstract <T> T getResult(ClientServiceRequestPacket clientServiceRequestPacket, Class<T> clazz);
}
