package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ServerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.server.utils.ServiceUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.NO_SERVICE;


/**
 * @Auther: william
 * @Date: 18/10/19 10:09
 * @Description:
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientServiceRequestHandler extends SimpleChannelInboundHandler<ClientServiceRequestPacket> {

    public static final ClientServiceRequestHandler INSTANCE = new ClientServiceRequestHandler();

    private ClientServiceRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientServiceRequestPacket clientServiceRequestPacket) throws Exception {
        List<Channel> channels = ServiceUtil.getChannels(clientServiceRequestPacket.getToServiceName());
        if (channels == null || channels.size() <= 0) {
            log.warn("(" + clientServiceRequestPacket.getToServiceName() + ")RPC服务不存在");
            ClientServiceResponsePacket clientServiceResponsePacket = new ClientServiceResponsePacket();
            clientServiceResponsePacket.setRequestId(clientServiceRequestPacket.getRequestId());
            clientServiceResponsePacket.setCode(NO_SERVICE);
            clientServiceResponsePacket.setDesc("(" + clientServiceRequestPacket.getToServiceName() + ")RPC服务不存在");
            clientServiceResponsePacket.setResult(null);
            channelHandlerContext.writeAndFlush(clientServiceResponsePacket);
        } else {
            channels.forEach(channel -> {
                ServerServiceRequestPacket serverServiceRequestPacket = new ServerServiceRequestPacket();
                serverServiceRequestPacket.setFromServiceId(clientServiceRequestPacket.getFromServiceId());
                serverServiceRequestPacket.setFromServiceName(clientServiceRequestPacket.getFromServiceName());
                serverServiceRequestPacket.setRequestId(clientServiceRequestPacket.getRequestId());
                serverServiceRequestPacket.setBeanName(clientServiceRequestPacket.getBeanName());
                serverServiceRequestPacket.setMethodName(clientServiceRequestPacket.getMethodName());
                serverServiceRequestPacket.setParamTypes(clientServiceRequestPacket.getParamTypes());
                serverServiceRequestPacket.setParams(clientServiceRequestPacket.getParams());
                channel.writeAndFlush(serverServiceRequestPacket);
            });
        }
    }

}
