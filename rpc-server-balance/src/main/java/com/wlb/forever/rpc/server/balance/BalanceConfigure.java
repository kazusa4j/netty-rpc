package com.wlb.forever.rpc.server.balance;

import com.wlb.forever.rpc.server.balance.cache.BalanceModeCache;
import com.wlb.forever.rpc.server.balance.cache.JDKBalanceModeCache;
import com.wlb.forever.rpc.server.balance.cache.RedisBalanceModeCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: william
 * @Date: 18/10/31 17:11
 * @Description:
 */
@Configuration
public class BalanceConfigure {
    @Bean
    @ConditionalOnClass(BalanceModeCache.class)
    @ConditionalOnMissingBean(BalanceModeCache.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.balance.cache", havingValue = "JDK")
    public BalanceModeCache jdkBalanceModeCache() {
        return new JDKBalanceModeCache();
    }

    @Bean
    @ConditionalOnClass(BalanceModeCache.class)
    @ConditionalOnMissingBean(BalanceModeCache.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.balance.cache", havingValue = "REDIS")
    public BalanceModeCache redisBalanceModeCache() {
        return new RedisBalanceModeCache();
    }
}
