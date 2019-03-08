package com.wlb.forever.rpc.client.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.UserHttpService;
import com.wlb.forever.rpc.utils.HttpClientUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: william
 * @Date: 18/11/15 14:14
 * @Description:
 */
@Service
public class UserHttpServiceImpl implements UserHttpService {
    @Override
    public User getUser() throws Exception {
        String result=HttpClientUtils.executeGetString("http://127.0.0.1:8001/producer/test", "utf-8", 3000);
        User user=JSON.parseObject(result, User.class);
        return user;
    }
}
