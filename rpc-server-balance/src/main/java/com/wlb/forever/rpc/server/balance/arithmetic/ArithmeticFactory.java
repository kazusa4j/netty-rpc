package com.wlb.forever.rpc.server.balance.arithmetic;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.balance.BalanceArithmeticFactory;
import com.wlb.forever.rpc.server.balance.arithmetic.random.RandomArithmetic;
import com.wlb.forever.rpc.server.balance.arithmetic.round.RoundArithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 17:23
 * @Description:
 */
public class ArithmeticFactory implements BalanceArithmeticFactory {
    /**
     * 获取负载均衡算法
     *
     * @param arithmeticName
     * @return
     */
    @Override
    public BalanceArithmetic getArithmetic(String arithmeticName) {
        switch (arithmeticName) {
            case "RANDOM":
                return RandomArithmetic.getInstance();
            case "ROUND":
                return RoundArithmetic.getInstance();
            default:
                return null;
        }
    }

    /**
     * 获取负载均衡算法列表
     *
     * @param arithmeticNames
     * @return
     */
    @Override
    public List<BalanceArithmetic> getArithmetics(String[] arithmeticNames) {
        List<BalanceArithmetic> list = new ArrayList<>();
        for (String arithmeticName : arithmeticNames) {
            BalanceArithmetic balanceArithmetic = getArithmetic(arithmeticName);
            if (balanceArithmetic != null) {
                list.add(balanceArithmetic);
            }
        }
        return list;
    }
}
