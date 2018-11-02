package com.wlb.forever.rpc.server.balance.arithmetic.random;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.balance.arithmetic.BalanceArithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: william
 * @Date: 18/11/2 09:29
 * @Description:
 */
public class randomArithmetic implements BalanceArithmetic {
    private randomArithmetic() {

    }

    private static class randomArithmeticHolder {
        private static final randomArithmetic INSTANCE = new randomArithmetic();
    }

    public static randomArithmetic getInstance() {
        return randomArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        Random random = new Random();
        int n = random.nextInt(producerServices.size());
        System.out.println("随机数"+n);
        List<Service> services = new ArrayList<>();
        services.add(producerServices.get(n));
        return services;
    }
}
