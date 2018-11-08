package com.wlb.forever.rpc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/11/7 16:10
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "wlb.rpc.server")
public class RpcServerConfig {
    private String host="127.0.0.1";
    private int port=11211;
}
