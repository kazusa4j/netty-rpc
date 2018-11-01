package com.wlb.forever.rpc.server.balance.cache;

import com.wlb.forever.rpc.common.server.balance.BalanceMode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/31 16:06
 * @Description:
 */
public class JDKBalanceModeCache implements BalanceModeCache {
    private static Map<String, BalanceMode> balanceModeMap = new ConcurrentHashMap<>();

    @Override
    public void put(String requestId, BalanceMode balanceMode) {
        balanceModeMap.put(requestId, balanceMode);
    }

    @Override
    public BalanceMode getBalanceMode(String requestId) {
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
