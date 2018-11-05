package com.wlb.forever.rpc.server.balance.arithmetic.leastconn;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.entity.Service;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/5 11:31
 * @Description:
 */
public class LeastConnArithmetic implements BalanceArithmetic {
    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        return null;
    }
}
