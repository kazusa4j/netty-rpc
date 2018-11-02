package com.wlb.forever.rpc.server.balance.arithmetic.round;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.server.balance.arithmetic.random.RandomArithmetic;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 09:28
 * @Description:
 */
public class RoundArithmetic implements BalanceArithmetic {
    private RoundArithmetic() {

    }

    private static class roundArithmeticHolder {
        private static final RoundArithmetic INSTANCE = new RoundArithmetic();
    }

    public static RoundArithmetic getInstance() {
        return roundArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        return producerServices;
    }
}
