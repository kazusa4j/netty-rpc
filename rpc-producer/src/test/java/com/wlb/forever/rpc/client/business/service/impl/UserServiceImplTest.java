package com.wlb.forever.rpc.client.business.service.impl;

import com.wlb.forever.rpc.client.business.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Auther: william
 * @Date: 18/10/26 16:40
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    private UserService userServiceImpl;

    @Test
    public void getUser() {
    }

    @Test
    public void getBeans() {
        userServiceImpl.getBeans();
    }
}