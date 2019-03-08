package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.server.executor.factory.CustomServerExecuteModeFactory;
import com.wlb.forever.rpc.server.executor.factory.ServerExecuteModeFactory;
import com.wlb.forever.rpc.server.executor.cache.ExecuteModeCache;
import com.wlb.forever.rpc.server.executor.cache.JDKExecuteModeCache;
import com.wlb.forever.rpc.server.executor.cache.RedisExecuteModeCache;
import com.wlb.forever.rpc.server.executor.factory.DefaultServerExecuteModeFactory;
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
public class ServerExecuteConfigure {
    @Bean
    @ConditionalOnMissingBean(ServerExecuteModeFactory.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.execute.factory", havingValue = "DEFAULT", matchIfMissing = true)
    public ServerExecuteModeFactory defaultExecuteModeFactory() {
        return new DefaultServerExecuteModeFactory();
    }

    @Bean
    @ConditionalOnMissingBean(ServerExecuteModeFactory.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.execute.factory", havingValue = "CUSTOM")
    public ServerExecuteModeFactory customExecuteModeFactory() {
        return new CustomServerExecuteModeFactory();
    }

    @Bean
    @ConditionalOnClass(ExecuteModeCache.class)
    @ConditionalOnMissingBean(ExecuteModeCache.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.execute.cache", havingValue = "JDK")
    public ExecuteModeCache jdkBalanceModeCache() {
        return new JDKExecuteModeCache();
    }

    @Bean
    @ConditionalOnClass(ExecuteModeCache.class)
    @ConditionalOnMissingBean(ExecuteModeCache.class)
    @ConditionalOnProperty(name = "wlb.rpc.server.execute.cache", havingValue = "REDIS")
    public ExecuteModeCache redisBalanceModeCache() {
        return new RedisExecuteModeCache();
    }
}
