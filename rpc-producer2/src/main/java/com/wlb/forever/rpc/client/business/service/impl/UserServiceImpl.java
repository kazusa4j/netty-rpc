package com.wlb.forever.rpc.client.business.service.impl;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: william
 * @Date: 18/10/18 09:42
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser() {
        User user = new User();
        user.setId(0);
        user.setName("kazusa2");
        user.setCreateTime(new Date());
        return user;
    }

    @Override
    public User getUser(String a, String b) {
        System.out.println("==="+b+"===");
        User user = new User();
        user.setId(a.hashCode());
        user.setName(b);
        user.setCreateTime(new Date());
        return user;
    }
}
