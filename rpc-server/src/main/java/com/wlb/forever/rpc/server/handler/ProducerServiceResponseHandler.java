package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.server.executor.ExecutorLoader;
import com.wlb.forever.rpc.server.executor.ProducerResponseExecutor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/20 13:46
 * @Description: 接收服务端返回RPC调用结果HANDLER
 */
@Slf4j
@ChannelHandler.Sharable
public class ProducerServiceResponseHandler extends SimpleChannelInboundHandler<ProducerServiceResponsePacket> {

    public static final ProducerServiceResponseHandler INSTANCE = new ProducerServiceResponseHandler();

    private ProducerServiceResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProducerServiceResponsePacket producerServiceResponsePacket) throws Exception {
        ProducerResponseExecutor producerResponseExecutor = ExecutorLoader.SERVER_RESPONSE_EXECUTOR;
        producerResponseExecutor.executeTask(channelHandlerContext, producerServiceResponsePacket);
    }

}
