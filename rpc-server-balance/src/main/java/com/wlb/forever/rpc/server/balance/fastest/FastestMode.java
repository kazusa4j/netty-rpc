package com.wlb.forever.rpc.server.balance.fastest;

import com.wlb.forever.rpc.common.constant.RpcResponseCode;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;
import com.wlb.forever.rpc.server.balance.BalanceMode;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 14:04
 * @Description: 最快模式
 */
@Slf4j
public class FastestMode extends AbstractBalanceMode implements BalanceMode {

    public FastestMode(Channel fromChannel, List<String> channelServiceIds) {
        super(fromChannel, channelServiceIds);
    }

    @Override
    public void requestProducer(ProducerServiceRequestPacket producerServiceRequestPacket) {
        String toServiceName = producerServiceRequestPacket.getRpcRequestInfo().getToServiceName();
        modeSignature.getChannelServiceIds().forEach(serviceId -> {
            Channel channel = ServiceUtil.getChannel(serviceId, toServiceName);
            channel.writeAndFlush(producerServiceRequestPacket);
        });
    }

    @Override
    public boolean responseConsumer(Channel channel, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        Service service = ServiceUtil.getService(channel);
        if (!modeSignature.getChannelServiceIds().contains(service.getServiceId())) {
            log.error("返回请求结果，服务生产者CHANNEL不匹配");
            return false;
        }
        if (consumerServiceResponsePacket.getRpcResponseInfo().getCode() != RpcResponseCode.SUCCESS) {
            modeSignature.getChannelServiceIds().remove(service.getServiceId());
            if (modeSignature.getChannelServiceIds().size() > 0) {
                return false;
            } else {
                return true;
            }
        }
        if (modeSignature.getFromChannel() != null && modeSignature.getFromChannel().isActive()) {
            modeSignature.getFromChannel().writeAndFlush(consumerServiceResponsePacket);
        }
        return true;
    }

}
