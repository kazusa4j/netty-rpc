package com.wlb.forever.rpc.client.business.service;


import com.wlb.forever.rpc.client.annotation.RpcClient;
import com.wlb.forever.rpc.client.business.entity.User;

/**
 * @Auther: william
 * @Date: 18/10/18 10:50
 * @Description:
 */
@RpcClient(serviceName = "producer")
public interface UserService {
    void getBeans();

    User getUser();

    User getUser(String a, String b);

    User getUser(String a);

    void save(User user) throws Exception;
}
