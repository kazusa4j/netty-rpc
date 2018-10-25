package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ServerServiceResponsePacket;
import com.wlb.forever.rpc.server.utils.ServiceUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/20 13:46
 * @Description:
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerServiceResponseHandler extends SimpleChannelInboundHandler<ServerServiceResponsePacket> {

    public static final ServerServiceResponseHandler INSTANCE = new ServerServiceResponseHandler();

    private ServerServiceResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ServerServiceResponsePacket serverServiceResponsePacket) throws Exception {
        ClientServiceResponsePacket clientServiceResponsePacket = new ClientServiceResponsePacket();
        clientServiceResponsePacket.setRequestId(serverServiceResponsePacket.getRequestId());
        clientServiceResponsePacket.setCode(serverServiceResponsePacket.getCode());
        clientServiceResponsePacket.setDesc(serverServiceResponsePacket.getDesc());
        clientServiceResponsePacket.setResult(serverServiceResponsePacket.getResult());
        Channel channel = ServiceUtil.getChannel(serverServiceResponsePacket.getFromServiceId(), serverServiceResponsePacket.getFromServiceName());
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(clientServiceResponsePacket);
        }
    }
}
