package com.wlb.forever.rpc;

import com.wlb.forever.rpc.client.annotation.EnableRpcClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: william
 * @Date: 18/10/17 15:43
 * @Description:
 */
@SpringBootApplication
@EnableRpcClients
public class NettyRPCConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyRPCConsumerApplication.class, args);
    }
}
