package com.wlb.forever.rpc.client.handler;

import com.wlb.forever.rpc.client.RpcClientStarter;
import com.wlb.forever.rpc.common.protocol.request.HeartBeatRequestPacket;
import com.wlb.forever.rpc.common.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: william
 * @Date: 18/10/18 09:24
 * @Description: 心跳请求HANDLER
 */
@Slf4j
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 10;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(() -> {
            ctx.writeAndFlush(new HeartBeatRequestPacket());
            //log.info("客户端发送心跳:" + System.currentTimeMillis());
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.executor().shutdownGracefully();
        if (RpcClientStarter.STATUS == 3) {
            RpcClientStarter.STATUS = 1;
            log.error("与RPC服务起连接断开，开始重连");
            RpcClientStarter rpcClientStarter = SpringBeanUtil.getBean(RpcClientStarter.class);
            rpcClientStarter.start();
        }
    }
}
