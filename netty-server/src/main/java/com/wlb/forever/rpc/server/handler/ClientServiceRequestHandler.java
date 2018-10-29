package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.SpringBeanUtil;
import com.wlb.forever.rpc.server.executor.ClientRequestExecutor;
import com.wlb.forever.rpc.server.executor.ExecutorLoader;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.SERVER_EXCEPTION;


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

        try {
            ClientRequestExecutor clientRequestExecutor = ExecutorLoader.CLIENT_REQUEST_EXECUTOR;
            clientRequestExecutor.executeTask(channelHandlerContext, clientServiceRequestPacket);
        } catch (Exception e) {
            ClientServiceResponsePacket clientServiceResponsePacket = new ClientServiceResponsePacket();
            clientServiceResponsePacket.setRequestId(clientServiceRequestPacket.getRequestId());
            clientServiceResponsePacket.setCode(SERVER_EXCEPTION);
            clientServiceResponsePacket.setDesc("RPC服务器出现异常");
            clientServiceResponsePacket.setResult(null);
            channelHandlerContext.writeAndFlush(clientServiceResponsePacket);
            log.warn("{}调用{}的RPC服务出现异常", clientServiceRequestPacket.getFromServiceName(), clientServiceRequestPacket.getToServiceName());
        }

    }

}
