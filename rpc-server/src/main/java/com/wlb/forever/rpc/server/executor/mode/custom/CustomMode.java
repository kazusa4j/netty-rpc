package com.wlb.forever.rpc.server.executor.mode.custom;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.executor.mode.AbstractServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.requestproducer.impl.RequestEntireProducer;
import com.wlb.forever.rpc.server.executor.responseconsumer.impl.FastestSuccessResponseConsumer;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 16:58
 * @Description:
 */
public class CustomMode extends AbstractServerRpcExecuteMode {
    public CustomMode(List<BalanceArithmetic> balanceArithmeticList, Service consumerService, List<Service> producerServices) {
        super(consumerService, producerServices);
        requestProducer = RequestEntireProducer.getInstance();
        responseConsumer = FastestSuccessResponseConsumer.getInstance();
        for (int i = 0; i < balanceArithmeticList.size(); i++) {
            addArithmetic(balanceArithmeticList.get(i));
        }
    }
}
