package com.wlb.forever.rpc.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/10/31 14:38
 * @Description: RPC服务器配置
 */
@Component
public class RpcServerConfig {
    /**
     * 是否开启负载均衡模式
     */
    public static boolean BALANCE_ENABLE;

    @Value("${wlb.rpc.server.execute.enable:true}")
    private void setBalanceEnable(boolean balanceEnable) {
        this.BALANCE_ENABLE = balanceEnable;
    }

    public static String BALANCE_ARITHMETIC;

    @Value("${wlb.rpc.server.execute.balance-arithmetic:RANDOM}")
    private void setBalanceArithmetic(String balanceArithmetic) {
        this.BALANCE_ARITHMETIC = balanceArithmetic;
    }

    /**
     * 负载均衡模式设置
     */
    public static String BALANCE_MODE;

    @Value("${wlb.rpc.server.execute.mode:NULL}")
    private void setBalanceMode(String balanceMode) {
        this.BALANCE_MODE = balanceMode;
    }
}
