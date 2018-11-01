package com.wlb.forever.rpc.server.balance;

import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.requestproducer.RequestProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.ResponseConsumer;
import lombok.Data;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 14:12
 * @Description:
 */
public abstract class AbstractBalanceMode implements BalanceMode {
    private ModeSignature modeSignature;
    protected RequestProducer requestProducer;
    protected ResponseConsumer responseConsumer;

    protected AbstractBalanceMode(String consumerServiceId, String consumerServiceName, List<String> producerServiceIds) {
        this.modeSignature = new ModeSignature(consumerServiceId, consumerServiceName, producerServiceIds);
    }

    public void requestProducer(ProducerServiceRequestPacket producerServiceRequestPacket) {
        requestProducer.requestProducer(modeSignature.producerServiceIds, producerServiceRequestPacket);
    }

    public boolean responseConsumer(String serviceId, ConsumerServiceResponsePacket consumerServiceResponsePacket) {
        return responseConsumer.responseConsumer(modeSignature.getConsumerServiceId(), modeSignature.getConsumerServiceName(), serviceId, modeSignature.getProducerServiceIds(), consumerServiceResponsePacket);
    }

    @Data
    public static class ModeSignature {
        private String consumerServiceId;
        private String consumerServiceName;
        private List<String> producerServiceIds;

        ModeSignature(String consumerServiceId, String consumerServiceName, List<String> producerServiceIds) {
            this.consumerServiceId = consumerServiceId;
            this.consumerServiceName = consumerServiceName;
            this.producerServiceIds = producerServiceIds;
        }
    }
}
