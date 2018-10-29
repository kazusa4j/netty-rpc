package com.wlb.forever.rpc.client.handler;

import com.wlb.forever.rpc.client.call.RpcJsonCaller;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/19 11:53
 * @Description:
 */
@ChannelHandler.Sharable
public class ClientServiceResponseHandler extends SimpleChannelInboundHandler<ConsumerServiceResponsePacket> {
    public static Map<String, RpcJsonCaller> messageMap = new ConcurrentHashMap<>();

    private ClientServiceResponseHandler() {

    }

    private static class ClientServiceResponseHandlerHolder {
        private static final ClientServiceResponseHandler INSTANCE = new ClientServiceResponseHandler();
    }

    public static ClientServiceResponseHandler getInstance() {
        return ClientServiceResponseHandlerHolder.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ConsumerServiceResponsePacket consumerServiceResponsePacket) throws Exception {
        String requestId = consumerServiceResponsePacket.getRequestId();
        if (messageMap.containsKey(requestId)){
            messageMap.get(requestId).over(consumerServiceResponsePacket);
            messageMap.remove(requestId);
        }
    }

    public static void clearOverRequest(String requestId){
        if (messageMap.containsKey(requestId)){
            messageMap.remove(requestId);
        }
    }


}
