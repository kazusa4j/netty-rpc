package com.wlb.forever.rpc.server.executor.factory;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 16:00
 * @Description:
 */
public interface ServerExecuteModeFactory {
     ServerRpcExecuteMode getExecuteMode(Service consumerService, List<Service> producerServices);

}
