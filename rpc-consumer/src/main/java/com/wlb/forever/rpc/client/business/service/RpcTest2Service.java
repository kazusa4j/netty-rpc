package com.wlb.forever.rpc.client.business.service;

import com.wlb.forever.rpc.client.annotation.RpcClient;
import com.wlb.forever.rpc.client.business.entity.User;

/**
 * @Auther: william
 * @Date: 18/10/23 11:10
 * @Description:
 */
public interface RpcTest2Service {
    User getUser() throws Exception;

    User getUser(String id) throws Exception;
}
