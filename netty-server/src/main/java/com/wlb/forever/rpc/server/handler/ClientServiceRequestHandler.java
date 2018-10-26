package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.server.executor.ClientRequestExecutor;
import com.wlb.forever.rpc.server.executor.ExecutorLoader;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


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
        ClientRequestExecutor clientRequestExecutor=ExecutorLoader.CLIENT_REQUEST_EXECUTOR;
        if(clientRequestExecutor==null){

        }else {
            clientRequestExecutor.execute(channelHandlerContext,clientServiceRequestPacket);
        }
    }

}
