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
    public static Integer userid = 0;

    @Override
    public User getUser() {
        User user = new User();
        user.setId(userid);
        user.setName("kazusa");
        user.setCreateTime(new Date());
        userid++;
        return user;
    }
}