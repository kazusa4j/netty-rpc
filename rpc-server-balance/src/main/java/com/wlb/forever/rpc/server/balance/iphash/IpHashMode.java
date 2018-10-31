package com.wlb.forever.rpc.server.balance.iphash;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;
import com.wlb.forever.rpc.server.balance.BalanceMode;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 15:34
 * @Description:
 */
public class IpHashMode extends AbstractBalanceMode implements BalanceMode {
    IpHashMode(Channel fromChannel, List<String> channelServiceIds) {
        super(fromChannel, channelServiceIds);
    }

    @Override
    public void requestProducer(ProducerServiceRequestPacket producerServiceRequestPacket) {

    }

    @Override
    public boolean responseConsumer(Channel channel, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        return false;
    }
}
