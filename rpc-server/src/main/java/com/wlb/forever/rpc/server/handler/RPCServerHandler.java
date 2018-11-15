package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
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
 * @Description: RPC服务器业务HANDLER
 */
@ChannelHandler.Sharable
public class RPCServerHandler extends SimpleChannelInboundHandler<AbstractPacket> {
    public static final RPCServerHandler INSTANCE = new RPCServerHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends AbstractPacket>> handlerMap;

    private RPCServerHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(CONSUMER_SERVICE_REQUEST, ConsumerServiceRequestHandler.INSTANCE);
        handlerMap.put(PRODUCER_SERVICE_RESPONSE, ProducerServiceResponseHandler.INSTANCE);
        handlerMap.put(REGISTER_SERVER_REQUEST, RegisterRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket abstractPacket) throws Exception {
        if (handlerMap.containsKey(abstractPacket.getCommand())) {
            handlerMap.get(abstractPacket.getCommand()).channelRead(ctx, abstractPacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ServiceUtil.unBindService(ctx.channel());
    }
}
