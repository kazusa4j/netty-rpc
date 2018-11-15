package com.wlb.forever.rpc.client.config;

import com.wlb.forever.rpc.client.RpcClientStarter;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: william
 * @Date: 18/11/7 17:34
 * @Description:
 */
@Configuration
public class RpcClientConfigure {
    @Bean
    @ConditionalOnBean(RpcClientStarter.class)
    public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
        return new CustomPropertyPlaceholderConfigurer();
    }
}
