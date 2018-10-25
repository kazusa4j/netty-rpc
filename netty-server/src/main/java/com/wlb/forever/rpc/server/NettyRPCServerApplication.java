package com.wlb.forever.rpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: william
 * @Date: 18/10/17 15:43
 * @Description:
 */
@SpringBootApplication
public class NettyRPCServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyRPCServerApplication.class, args);
    }
}
