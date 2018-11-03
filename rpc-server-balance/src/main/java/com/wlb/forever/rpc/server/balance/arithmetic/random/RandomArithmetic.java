package com.wlb.forever.rpc.server.balance.arithmetic.random;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: william
 * @Date: 18/11/2 09:29
 * @Description:
 */
@Slf4j
public class RandomArithmetic implements BalanceArithmetic {
    private RandomArithmetic() {

    }

    private static class randomArithmeticHolder {
        private static final RandomArithmetic INSTANCE = new RandomArithmetic();
    }

    public static RandomArithmetic getInstance() {
        return randomArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        log.info("调用随机算法");
        Random random = new Random();
        int n = random.nextInt(producerServices.size());

        List<Service> services = new ArrayList<>();
        services.add(producerServices.get(n));
        return services;
    }
}
