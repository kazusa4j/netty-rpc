package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.server.executor.ConsumerRequestExecutor;
import com.wlb.forever.rpc.server.executor.ExecutorLoader;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.SERVER_EXCEPTION;


/**
 * @Auther: william
 * @Date: 18/10/19 10:09
 * @Description: 接收客户端RPC调用HANDLER
 */
@Slf4j
@ChannelHandler.Sharable
public class ConsumerServiceRequestHandler extends SimpleChannelInboundHandler<ConsumerServiceRequestPacket> {

    public static final ConsumerServiceRequestHandler INSTANCE = new ConsumerServiceRequestHandler();

    private ConsumerServiceRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ConsumerServiceRequestPacket consumerServiceRequestPacket) throws Exception {

        try {
            ConsumerRequestExecutor consumerRequestExecutor = ExecutorLoader.CLIENT_REQUEST_EXECUTOR;
            consumerRequestExecutor.executeTask(channelHandlerContext, consumerServiceRequestPacket);
        } catch (Exception e) {
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            RpcResponseInfo rpcResponseInfo = new RpcResponseInfo();
            rpcResponseInfo.setRequestId(consumerServiceRequestPacket.getRpcRequestInfo().getRequestId());
            rpcResponseInfo.setCode(SERVER_EXCEPTION);
            rpcResponseInfo.setDesc("RPC服务器出现异常");
            rpcResponseInfo.setResult(null);
            consumerServiceResponsePacket.setRpcResponseInfo(rpcResponseInfo);
            channelHandlerContext.writeAndFlush(consumerServiceResponsePacket);
            log.warn("{}调用{}的RPC服务出现异常", consumerServiceRequestPacket.getRpcRequestInfo().getFromServiceName(), consumerServiceRequestPacket.getRpcRequestInfo().getToServiceName());
        }

    }

}
