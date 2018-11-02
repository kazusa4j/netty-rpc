package com.wlb.forever.rpc.server.balance.arithmetic.round;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.balance.arithmetic.BalanceArithmetic;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 09:28
 * @Description:
 */
public class roundArithmetic implements BalanceArithmetic {

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        return producerServices;
    }
}
