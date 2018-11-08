package com.wlb.forever.rpc.server.balance.arithmetic.nothing;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.entity.Service;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/6 15:11
 * @Description:
 */
public class NullArithmetic implements BalanceArithmetic {

    private NullArithmetic() {

    }

    private static class NullArithmeticHolder {
        private static final NullArithmetic INSTANCE = new NullArithmetic();
    }

    public static NullArithmetic getInstance() {
        return NullArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        return producerServices;
    }
}
