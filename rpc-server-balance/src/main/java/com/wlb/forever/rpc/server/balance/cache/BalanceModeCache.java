package com.wlb.forever.rpc.server.balance.cache;

import com.wlb.forever.rpc.common.server.balance.BalanceMode;

/**
 * @Auther: william
 * @Date: 18/10/31 16:05
 * @Description:
 */
public interface BalanceModeCache {

    void removeBalanceMode(String requestId);

    void put(String requestId, BalanceMode balanceMode);

    BalanceMode getBalanceMode(String requestId);
}
