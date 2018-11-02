package com.wlb.forever.rpc.server.balance;

import com.wlb.forever.rpc.common.balance.BalanceArithmeticFactory;
import com.wlb.forever.rpc.server.balance.arithmetic.ArithmeticFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: william
 * @Date: 18/11/2 17:31
 * @Description:
 */
@Configuration
public class BalanceConfigure {
    @Bean
    @ConditionalOnMissingBean(BalanceArithmeticFactory.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.balance.arithmetic", havingValue = "true")
    public BalanceArithmeticFactory arithmeticFactory() {
        return new ArithmeticFactory();
    }
}
