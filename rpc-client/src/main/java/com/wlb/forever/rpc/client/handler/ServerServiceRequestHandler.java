package com.wlb.forever.rpc.client.handler;

import com.wlb.forever.rpc.client.executor.MessageSendExecutor;
import com.wlb.forever.rpc.client.executor.MessageSendExecutorLoader;
import com.wlb.forever.rpc.common.protocol.request.ServerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ServerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.SpringContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Auther: william
 * @Date: 18/10/20 13:26
 * @Description:
 */
@ChannelHandler.Sharable
@Slf4j
public class ServerServiceRequestHandler extends SimpleChannelInboundHandler<ServerServiceRequestPacket> {
    public static final ServerServiceRequestHandler INSTANCE = new ServerServiceRequestHandler();

    private ServerServiceRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ServerServiceRequestPacket serverServiceRequestPacket) throws Exception {
        MessageSendExecutor messageSendExecutor = MessageSendExecutorLoader.messageSendExecutor;
        messageSendExecutor.send(serverServiceRequestPacket, channelHandlerContext);
    }
}
