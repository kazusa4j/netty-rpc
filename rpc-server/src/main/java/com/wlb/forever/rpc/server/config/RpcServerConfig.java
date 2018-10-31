package com.wlb.forever.rpc.server.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Auther: william
 * @Date: 18/10/31 14:38
 * @Description: RPC服务器配置
 */
public class RpcServerConfig {
    /**
     * 负载均衡模式设置
     */
    public static String BALANCE_MODE;

    @Value("${wlb.rpc.server.balance-mode:NULL}")
    private void setBalanceMode(String balanceMode) {
        this.BALANCE_MODE = balanceMode;
    }
}
