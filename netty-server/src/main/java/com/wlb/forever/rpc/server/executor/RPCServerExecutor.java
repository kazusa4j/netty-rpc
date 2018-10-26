package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Auther: william
 * @Date: 18/10/26 09:53
 * @Description:
 */
public interface RPCServerExecutor {
    void execute(ChannelHandlerContext ch, Packet packet);
}
