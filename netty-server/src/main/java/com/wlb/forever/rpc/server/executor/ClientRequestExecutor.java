package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ServerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.server.utils.ServiceUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wlb.forever.rpc.common.constant.RpcResponseCode.NO_SERVICE;

/**
 * @Auther: william
 * @Date: 18/10/26 09:38
 * @Description:
 */
@Component
@Slf4j
public class ClientRequestExecutor {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolClientRequest;//变量名称为定义的线程池bean定义的name属性名。

    @Async(value = "threadPoolClientRequest")
    public void executeTask(ChannelHandlerContext ch, Packet packet) {
        ClientServiceRequestPacket clientServiceRequestPacket = (ClientServiceRequestPacket) packet;
        List<Channel> channels = ServiceUtil.getChannels(clientServiceRequestPacket.getToServiceName());
        if (channels == null || channels.size() <= 0) {
            log.warn("RPC服务({})不存在", clientServiceRequestPacket.getToServiceName());
            ClientServiceResponsePacket clientServiceResponsePacket = new ClientServiceResponsePacket();
            clientServiceResponsePacket.setRequestId(clientServiceRequestPacket.getRequestId());
            clientServiceResponsePacket.setCode(NO_SERVICE);
            clientServiceResponsePacket.setDesc("(" + clientServiceRequestPacket.getToServiceName() + ")RPC服务不存在");
            clientServiceResponsePacket.setResult(null);
            ch.writeAndFlush(clientServiceResponsePacket);
        } else {
            channels.forEach(channel -> {
                ServerServiceRequestPacket serverServiceRequestPacket = new ServerServiceRequestPacket();
                serverServiceRequestPacket.setFromServiceId(clientServiceRequestPacket.getFromServiceId());
                serverServiceRequestPacket.setFromServiceName(clientServiceRequestPacket.getFromServiceName());
                serverServiceRequestPacket.setRequestId(clientServiceRequestPacket.getRequestId());
                serverServiceRequestPacket.setBeanName(clientServiceRequestPacket.getBeanName());
                serverServiceRequestPacket.setMethodName(clientServiceRequestPacket.getMethodName());
                serverServiceRequestPacket.setParamTypes(clientServiceRequestPacket.getParamTypes());
                serverServiceRequestPacket.setParams(clientServiceRequestPacket.getParams());
                channel.writeAndFlush(serverServiceRequestPacket);
            });
        }
    }
}
