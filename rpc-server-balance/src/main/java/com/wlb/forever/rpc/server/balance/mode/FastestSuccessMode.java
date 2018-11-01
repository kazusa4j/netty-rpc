package com.wlb.forever.rpc.server.balance.mode;

import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;
import com.wlb.forever.rpc.server.balance.requestproducer.entire.RequestEntireProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.impl.FastestSuccessResponseConsumer;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 15:28
 * @Description:
 */
public class FastestSuccessMode extends AbstractBalanceMode {
    public FastestSuccessMode(String consumerServiceId, String consumerServiceName, List<String> producerServiceIds) {
        super(consumerServiceId, consumerServiceName, producerServiceIds);
        requestProducer = RequestEntireProducer.getInstance();
        responseConsumer = FastestSuccessResponseConsumer.getInstance();
    }
}
