package com.wlb.forever.rpc.server.executor.cache;

import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/31 16:06
 * @Description:
 */
public class JDKExecuteModeCache implements ExecuteModeCache {
    private static Map<String, ServerRpcExecuteMode> balanceModeMap = new ConcurrentHashMap<>();

    @Override
    public void put(String requestId, ServerRpcExecuteMode serverRpcExecuteMode) {
        balanceModeMap.put(requestId, serverRpcExecuteMode);
    }

    @Override
    public ServerRpcExecuteMode getBalanceMode(String requestId) {
        synchronized (balanceModeMap) {
            if (!balanceModeMap.containsKey(requestId)) {
                return null;
            }
            return balanceModeMap.get(requestId);
        }
    }

    @Override
    public void removeBalanceMode(String requestId) {
        if (balanceModeMap.containsKey(requestId)) {
            balanceModeMap.remove(requestId);
        }
    }
}
