package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.wlb.forever.rpc.common.protocol.command.Command.CONSUMER_SERVICE_REQUEST;
import static com.wlb.forever.rpc.common.protocol.command.Command.PRODUCER_SERVICE_RESPONSE;
import static com.wlb.forever.rpc.common.protocol.command.Command.REGISTER_SERVER_REQUEST;

/**
 * @Auther: william
 * @Date: 18/10/29 17:55
 * @Description:
 */
@ChannelHandler.Sharable
public class RPCServerHandler extends SimpleChannelInboundHandler<Packet> {
    public static final RPCServerHandler INSTANCE = new RPCServerHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private RPCServerHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(CONSUMER_SERVICE_REQUEST, ConsumerServiceRequestHandler.INSTANCE);
        handlerMap.put(PRODUCER_SERVICE_RESPONSE, ProducerServiceResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
