package com.wlb.forever.rpc.client.config;

import com.wlb.forever.rpc.common.entity.Service;
import static com.wlb.forever.rpc.client.RpcClientStarter.SERVICE_IP;
import static com.wlb.forever.rpc.client.RpcClientStarter.SERVICE_ID;
import static com.wlb.forever.rpc.client.RpcClientStarter.SERVICE_NAME;


/**
 * @Auther: william
 * @Date: 18/10/25 14:52
 * @Description: RPC 客户端配置
 */
public class RpcClientConfig {
    public static final Service SERVICE=new Service(SERVICE_ID,SERVICE_NAME,"",SERVICE_IP);

}
