package com.wlb.forever.rpc.common.balance;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 17:19
 * @Description:
 */
public interface BalanceArithmeticFactory {
    /**
     *
     * @param arithmeticName
     * @return
     */
    BalanceArithmetic getArithmetic(String arithmeticName);

    /**
     *
     * @return
     */
    List<BalanceArithmetic> getArithmetics();
}
