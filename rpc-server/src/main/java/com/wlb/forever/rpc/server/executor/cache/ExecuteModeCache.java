package com.wlb.forever.rpc.server.executor.cache;

import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;

/**
 * @Auther: william
 * @Date: 18/10/31 16:05
 * @Description:
 */
public interface ExecuteModeCache {

    void removeBalanceMode(String requestId);

    void put(String requestId, ServerRpcExecuteMode serverRpcExecuteMode);

    ServerRpcExecuteMode getBalanceMode(String requestId);
}
