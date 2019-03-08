package com.wlb.forever.rpc.client.business.controller;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.UserHttpService;
import com.wlb.forever.rpc.client.business.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: william
 * @Date: 18/11/5 09:14
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRpcControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserHttpService userHttpService;

    @Test
    public void test() {
        int count=10000;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            System.out.println(i);
            new Thread(() -> {
                try {
                    start.await();
                    userService.getUser();
                    //System.out.println(user.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            }).start();
        }
        long starttime=System.nanoTime();
        start.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endtime=System.nanoTime();
        System.out.println(endtime-starttime);
    }

    @Test
    public void test2() {


    }

    @Test
    public void getBeans() {
    }

    @Test
    public void save() {
    }
}