package com.wlb.forever.rpc.server.balance;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.arithmetic.BalanceArithmetic;
import com.wlb.forever.rpc.server.balance.requestproducer.RequestProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.ResponseConsumer;
import lombok.Data;

import java.util.ArrayList;
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
    private final List<BalanceArithmetic> balanceArithmeticList = new ArrayList<>();

    protected AbstractBalanceMode(Service consumerService, List<Service> producerServices) {
        this.modeSignature = new ModeSignature(consumerService, producerServices);
    }

    /**
     * 服务器请求生产者
     *
     * @param producerServiceRequestPacket
     */
    public void requestProducer(ProducerServiceRequestPacket producerServiceRequestPacket) {
        List<Service> producerServices = modeSignature.producerServices;
        if (producerServices.size() > 1) {
            for (BalanceArithmetic balanceArithmetic : balanceArithmeticList) {
                if (producerServices.size() == 1) {
                    break;
                }
                producerServices = balanceArithmetic.filterProducerServices(modeSignature.consumerService, producerServices);
            }
        }

        requestProducer.requestProducer(producerServices, producerServiceRequestPacket);
    }

    /**
     * 服务器返回消费者RPC调用结果
     *
     * @param serviceId
     * @param consumerServiceResponsePacket
     * @return
     */
    public boolean responseConsumer(String serviceId, ConsumerServiceResponsePacket consumerServiceResponsePacket) {

        return responseConsumer.responseConsumer(modeSignature.getConsumerService(), serviceId, modeSignature.getProducerServices(), consumerServiceResponsePacket);
    }

    /**
     * 添加过滤生产者负载均衡算法
     *
     * @param balanceArithmetic
     */
    public void addArithmetic(BalanceArithmetic balanceArithmetic) {
        balanceArithmeticList.add(balanceArithmetic);
    }

    /**
     *
     */
    @Data
    public static class ModeSignature {
        private Service consumerService;
        private List<Service> producerServices;

        ModeSignature(Service consumerService, List<Service> producerServices) {
            this.consumerService = consumerService;
            this.producerServices = producerServices;
        }
    }
}
