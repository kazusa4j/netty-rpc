package com.wlb.forever.rpc.server.attritube;

import com.wlb.forever.rpc.server.entity.Service;
import io.netty.util.AttributeKey;

/**
 * @Auther: william
 * @Date: 18/10/18 11:27
 * @Description: CHANNEL属性设置
 */
public class Attributes {
    public static final AttributeKey<Service> SERVICE = AttributeKey.newInstance("service");
}
