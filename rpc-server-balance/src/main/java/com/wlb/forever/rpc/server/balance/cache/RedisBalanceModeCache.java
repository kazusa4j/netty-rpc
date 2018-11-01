package com.wlb.forever.rpc.server.balance.cache;

import com.wlb.forever.rpc.common.server.balance.BalanceMode;

/**
 * @Auther: william
 * @Date: 18/10/31 16:07
 * @Description:
 */
public class RedisBalanceModeCache implements BalanceModeCache {
    @Override
    public void removeBalanceMode(String requestId) {

    }

    @Override
    public void put(String requestId, BalanceMode balanceMode) {

    }

    @Override
    public BalanceMode getBalanceMode(String requestId) {
        return null;
    }
}
