package com.wlb.forever.rpc.server.executor.mode.fastestsuccess;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.executor.mode.AbstractServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.requestproducer.impl.RequestEntireProducer;
import com.wlb.forever.rpc.server.executor.responseconsumer.impl.FastestSuccessResponseConsumer;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 15:42
 * @Description:
 */
public class FastestSuccessMode extends AbstractServerRpcExecuteMode {
    public FastestSuccessMode(Service consumerService, List<Service> producerServices) {
        super(consumerService, producerServices);
        requestProducer = RequestEntireProducer.getInstance();
        responseConsumer = FastestSuccessResponseConsumer.getInstance();
    }
}
