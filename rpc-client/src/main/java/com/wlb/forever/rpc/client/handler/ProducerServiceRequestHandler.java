package com.wlb.forever.rpc.client.handler;

import com.wlb.forever.rpc.client.executor.MessageSendExecutor;
import com.wlb.forever.rpc.client.executor.MessageSendExecutorLoader;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/20 13:26
 * @Description: 服务器RPC调用请求HANDLER
 */
@ChannelHandler.Sharable
@Slf4j
public class ProducerServiceRequestHandler extends SimpleChannelInboundHandler<ProducerServiceRequestPacket> {
    public static final ProducerServiceRequestHandler INSTANCE = new ProducerServiceRequestHandler();

    private ProducerServiceRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProducerServiceRequestPacket producerServiceRequestPacket) throws Exception {
        MessageSendExecutor messageSendExecutor = MessageSendExecutorLoader.MESSAGE_SEND_EXECUTOR;
        messageSendExecutor.send(producerServiceRequestPacket, channelHandlerContext);
    }
}
