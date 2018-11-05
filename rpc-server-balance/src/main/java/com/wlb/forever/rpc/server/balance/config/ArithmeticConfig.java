package com.wlb.forever.rpc.server.balance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/11/5 11:54
 * @Description:
 */
@Component
public class ArithmeticConfig {
    public static String BALANCE_ARITHMETIC;

    @Value("${wlb.rpc.server.balance.arithmetic:RANDOM}")
    private void setBalanceArithmetic(String balanceArithmetic) {
        this.BALANCE_ARITHMETIC = balanceArithmetic;
    }
}
