package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.request.HeartBeatRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.HeartBeatResponsePacket;
import com.wlb.forever.rpc.server.utils.ServiceUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/18 09:23
 * @Description:
 */
@ChannelHandler.Sharable
@Slf4j
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
