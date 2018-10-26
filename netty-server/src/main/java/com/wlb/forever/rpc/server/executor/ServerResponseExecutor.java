package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ServerServiceResponsePacket;
import com.wlb.forever.rpc.server.utils.ServiceUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/10/26 09:39
 * @Description:
 */
@Component
@Slf4j
public class ServerResponseExecutor {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolServerResponse;//变量名称为定义的线程池bean定义的name属性名。

    @Async(value = "threadPoolServerResponse")
    public void execute(ChannelHandlerContext ch, Packet packet) {
        ServerServiceResponsePacket serverServiceResponsePacket = (ServerServiceResponsePacket) packet;
        ClientServiceResponsePacket clientServiceResponsePacket = new ClientServiceResponsePacket();
        clientServiceResponsePacket.setRequestId(serverServiceResponsePacket.getRequestId());
        clientServiceResponsePacket.setCode(serverServiceResponsePacket.getCode());
        clientServiceResponsePacket.setDesc(serverServiceResponsePacket.getDesc());
        clientServiceResponsePacket.setResult(serverServiceResponsePacket.getResult());
        Channel channel = ServiceUtil.getChannel(serverServiceResponsePacket.getFromServiceId(), serverServiceResponsePacket.getFromServiceName());
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(clientServiceResponsePacket);
        }
    }
}
