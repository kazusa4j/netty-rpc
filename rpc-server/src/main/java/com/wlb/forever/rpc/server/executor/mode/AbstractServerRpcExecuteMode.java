package com.wlb.forever.rpc.server.executor.mode;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.server.executor.requestproducer.RequestProducer;
import com.wlb.forever.rpc.server.executor.responseconsumer.ResponseConsumer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 14:12
 * @Description:
 */
public abstract class AbstractServerRpcExecuteMode implements ServerRpcExecuteMode {
    private ModeSignature modeSignature;
    //请求生产者
    protected RequestProducer requestProducer;
    //返回消费者响应
    protected ResponseConsumer responseConsumer;
    //负载均衡算法
    private final List<BalanceArithmetic> balanceArithmeticList = new ArrayList<>();

    protected AbstractServerRpcExecuteMode(Service consumerService, List<Service> producerServices) {
        this.modeSignature = new ModeSignature(consumerService, producerServices);
    }

    /**
     * 服务器请求生产者
     *
     * @param producerServiceRequestPacket
     */
    @Override
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
    @Override
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
