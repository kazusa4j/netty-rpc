package com.wlb.forever.rpc.client.business.service;


import com.wlb.forever.rpc.client.business.entity.User;

/**
 * @Auther: william
 * @Date: 18/10/18 09:41
 * @Description:
 */
public interface UserService {

    User getUser();

    User getUser(String a,String b);
}
