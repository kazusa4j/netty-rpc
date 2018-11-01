package com.wlb.forever.rpc.server.balance.mode;

import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;
import com.wlb.forever.rpc.server.balance.requestproducer.entire.RequestEntireProducer;
import com.wlb.forever.rpc.server.balance.responseconsumer.impl.FastestResponseConsumer;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/1 15:11
 * @Description:
 */
public class FastestMode extends AbstractBalanceMode {


    public FastestMode(String consumerServiceId, String consumerServiceName, List<String> producerServiceIds) {
        super(consumerServiceId, consumerServiceName, producerServiceIds);
        requestProducer = RequestEntireProducer.getInstance();
        responseConsumer = FastestResponseConsumer.getInstance();
    }

}
