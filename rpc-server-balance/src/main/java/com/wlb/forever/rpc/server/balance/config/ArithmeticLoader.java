package com.wlb.forever.rpc.server.balance.config;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.balance.BalanceArithmeticFactory;
import com.wlb.forever.rpc.common.utils.StringUtil;
import com.wlb.forever.rpc.server.balance.arithmetic.iphash.IpHashArithmetic;
import com.wlb.forever.rpc.server.balance.arithmetic.random.RandomArithmetic;
import com.wlb.forever.rpc.server.balance.arithmetic.round.RoundArithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 17:23
 * @Description:
 */
public class ArithmeticLoader implements BalanceArithmeticFactory {
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
            case "IP_HASH":
                return IpHashArithmetic.getInstance();
            default:
                return null;
        }
    }

    /**
     * 获取负载均衡算法列表
     *
     * @return
     */
    @Override
    public List<BalanceArithmetic> getArithmetics() {
        String[] arithmeticNames = getArithmeticNames();
        List<BalanceArithmetic> list = new ArrayList<>();
        if (arithmeticNames != null) {
            for (String arithmeticName : arithmeticNames) {
                BalanceArithmetic balanceArithmetic = getArithmetic(arithmeticName);
                if (balanceArithmetic != null) {
                    list.add(balanceArithmetic);
                }
            }
        }
        return list;
    }

    private String[] getArithmeticNames() {
        String arithmeticNames = ArithmeticConfig.BALANCE_ARITHMETIC;
        if (!StringUtil.isBlank(arithmeticNames)) {
            String[] arithmeticNameArr = arithmeticNames.split(",");
            if (arithmeticNameArr.length > 0) {
                return arithmeticNameArr;
            } else {
                return new String[]{arithmeticNames};
            }
        }
        return null;
    }

}
