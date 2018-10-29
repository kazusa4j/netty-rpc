package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.server.executor.ExecutorLoader;
import com.wlb.forever.rpc.server.executor.ServerResponseExecutor;
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
public class ServerServiceResponseHandler extends SimpleChannelInboundHandler<ProducerServiceResponsePacket> {

    public static final ServerServiceResponseHandler INSTANCE = new ServerServiceResponseHandler();

    private ServerServiceResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProducerServiceResponsePacket producerServiceResponsePacket) throws Exception {
        ServerResponseExecutor serverResponseExecutor = ExecutorLoader.SERVER_RESPONSE_EXECUTOR;
        serverResponseExecutor.executeTask(channelHandlerContext, producerServiceResponsePacket);
    }

}
