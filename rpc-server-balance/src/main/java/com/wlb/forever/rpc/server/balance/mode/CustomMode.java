package com.wlb.forever.rpc.server.balance.mode;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.balance.AbstractBalanceMode;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 12:20
 * @Description:
 */
public class CustomMode extends AbstractBalanceMode {
    public CustomMode(Service consumerService, List<Service> producerServices) {
        super(consumerService, producerServices);
    }
}
