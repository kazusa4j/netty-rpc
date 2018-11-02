package com.wlb.forever.rpc.server.balance.arithmetic;

import com.wlb.forever.rpc.common.entity.Service;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 09:21
 * @Description:
 */
public interface BalanceArithmetic {
    List<Service> filterProducerServices(Service consumerService, List<Service> producerServices);
}
