package com.wlb.forever.rpc.server.balance;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 14:12
 * @Description:
 */
public abstract class AbstractBalanceMode {
    public ModeSignature modeSignature;

    protected AbstractBalanceMode(Channel fromChannel, List<String> channelServiceIds) {
        this.modeSignature = new ModeSignature(fromChannel, channelServiceIds);
    }

    @Data
    public static class ModeSignature {
        private Channel fromChannel;
        private List<String> channelServiceIds;

        ModeSignature(Channel fromChannel, List<String> channelServiceIds) {
            this.fromChannel = fromChannel;
            this.channelServiceIds = channelServiceIds;
        }
    }
}
