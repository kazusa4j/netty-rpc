package com.wlb.forever.rpc.common.balance;

import com.wlb.forever.rpc.common.entity.Service;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 09:21
 * @Description:
 */
public interface BalanceArithmetic {
    /**
     * 过滤生产者
     * @param consumerService
     * @param producerServices
     * @return
     */
    List<Service> filterProducerServices(Service consumerService, List<Service> producerServices);
}
