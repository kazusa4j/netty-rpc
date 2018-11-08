package com.wlb.forever.rpc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Auther: william
 * @Date: 18/10/25 14:52
 * @Description: RPC 客户端配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "wlb.rpc.client")
public class RpcClientConfig {
    private int initRetryTime=5;
    private Boolean awaysRetry=true;
    private int awaysRetryInterval=60;

    private static String serviceName;

    public void setServiceName(String serviceName) {
        RpcClientConfig.serviceName = serviceName;
    }

    public static String getServiceName() {
        return RpcClientConfig.serviceName;
    }

}
