package com.wlb.forever.rpc.client.business.service.impl;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.RpcTest2Service;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: william
 * @Date: 18/11/5 12:25
 * @Description:
 */
@Service
public class RpcTestServiceImpl implements RpcTest2Service {
    @Override
    public User getUser() throws Exception {
        User user = new User();
        user.setId(0);
        user.setName("kazusax");
        user.setCreateTime(new Date());
        return user;
    }

    @Override
    public User getUser(String id) throws Exception {
        return null;
    }
}
