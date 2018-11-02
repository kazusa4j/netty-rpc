package com.wlb.forever.rpc.server.balance.mode;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;
import com.wlb.forever.rpc.server.balance.arithmetic.random.randomArithmetic;
import com.wlb.forever.rpc.server.balance.requestproducer.entire.RequestEntireProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.impl.FastestResponseConsumer;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 10:35
 * @Description:
 */
public class RandomMode extends AbstractBalanceMode {
    public RandomMode(Service consumerService, List<Service> producerServices) {
        super(consumerService, producerServices);
        requestProducer = RequestEntireProducer.getInstance();
        responseConsumer = FastestResponseConsumer.getInstance();
        addArithmetic(randomArithmetic.getInstance());
    }
}
