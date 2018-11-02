package com.wlb.forever.rpc.server.executor.cache;

import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;

/**
 * @Auther: william
 * @Date: 18/10/31 16:07
 * @Description:
 */
public class RedisExecuteModeCache implements ExecuteModeCache {
    @Override
    public void removeBalanceMode(String requestId) {

    }

    @Override
    public void put(String requestId, ServerRpcExecuteMode serverRpcExecuteMode) {

    }

    @Override
    public ServerRpcExecuteMode getBalanceMode(String requestId) {
        return null;
    }
}
