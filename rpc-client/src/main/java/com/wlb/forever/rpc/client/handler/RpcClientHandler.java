package com.wlb.forever.rpc.client.handler;

import com.wlb.forever.rpc.common.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.wlb.forever.rpc.common.protocol.command.Command.*;

/**
 * @Auther: william
 * @Date: 18/10/30 14:39
 * @Description:
 */
@ChannelHandler.Sharable
public class RpcClientHandler extends SimpleChannelInboundHandler<Packet> {
    public static final RpcClientHandler INSTANCE = new RpcClientHandler();


    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private RpcClientHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(CONSUMER_SERVICE_RESPONSE, ConsumerServiceResponseHandler.getInstance());
        handlerMap.put(PRODUCER_SERVICE_REQUEST, ProducerServiceRequestHandler.INSTANCE);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if (handlerMap.containsKey(packet.getCommand())) {
            handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
        }
    }
}
