package com.wlb.forever.rpc.server.executor.factory;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.mode.fastestsuccess.FastestSuccessMode;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 15:52
 * @Description:
 */
public class DefaultServerExecuteModeFactory implements ServerExecuteModeFactory {

    @Override
    public ServerRpcExecuteMode getExecuteMode(Service consumerService, List<Service> producerServices) {
        return new FastestSuccessMode(consumerService, producerServices);
    }
}
