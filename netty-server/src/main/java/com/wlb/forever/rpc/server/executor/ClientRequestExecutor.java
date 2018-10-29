package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.protocol.Packet;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
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
        ConsumerServiceRequestPacket consumerServiceRequestPacket = (ConsumerServiceRequestPacket) packet;
        List<Channel> channels = ServiceUtil.getChannels(consumerServiceRequestPacket.getToServiceName());
        if (channels == null || channels.size() <= 0) {
            log.warn("RPC服务({})不存在", consumerServiceRequestPacket.getToServiceName());
            ConsumerServiceResponsePacket consumerServiceResponsePacket = new ConsumerServiceResponsePacket();
            consumerServiceResponsePacket.setRequestId(consumerServiceRequestPacket.getRequestId());
            consumerServiceResponsePacket.setCode(NO_SERVICE);
            consumerServiceResponsePacket.setDesc("(" + consumerServiceRequestPacket.getToServiceName() + ")RPC服务不存在");
            consumerServiceResponsePacket.setResult(null);
            ch.writeAndFlush(consumerServiceResponsePacket);
        } else {
            channels.forEach(channel -> {
                ProducerServiceRequestPacket producerServiceRequestPacket = new ProducerServiceRequestPacket();
                producerServiceRequestPacket.setFromServiceId(consumerServiceRequestPacket.getFromServiceId());
                producerServiceRequestPacket.setFromServiceName(consumerServiceRequestPacket.getFromServiceName());
                producerServiceRequestPacket.setRequestId(consumerServiceRequestPacket.getRequestId());
                producerServiceRequestPacket.setBeanName(consumerServiceRequestPacket.getBeanName());
                producerServiceRequestPacket.setMethodName(consumerServiceRequestPacket.getMethodName());
                producerServiceRequestPacket.setParamTypes(consumerServiceRequestPacket.getParamTypes());
                producerServiceRequestPacket.setParams(consumerServiceRequestPacket.getParams());
                channel.writeAndFlush(producerServiceRequestPacket);
            });
        }
    }
}
