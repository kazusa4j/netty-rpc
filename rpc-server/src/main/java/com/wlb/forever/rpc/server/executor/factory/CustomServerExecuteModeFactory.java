package com.wlb.forever.rpc.server.executor.factory;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.balance.BalanceArithmeticFactory;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.config.RpcServerConfig;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.mode.custom.CustomMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/2 17:36
 * @Description:
 */
public class CustomServerExecuteModeFactory implements ServerExecuteModeFactory {
    @Autowired
    private BalanceArithmeticFactory balanceArithmeticFactory;

    @Override
    public ServerRpcExecuteMode getExecuteMode(Service consumerService, List<Service> producerServices) {
        String arithmeticNames = RpcServerConfig.BALANCE_ARITHMETIC;
        List<BalanceArithmetic> list = new ArrayList<>();
        if (arithmeticNames != null && !"".equals(arithmeticNames)) {
            String[] arithmeticNameArr = arithmeticNames.split(",");
            if (arithmeticNameArr.length > 1) {
                list = balanceArithmeticFactory.getArithmetics(arithmeticNameArr);
            } else {
                BalanceArithmetic balanceArithmetic = balanceArithmeticFactory.getArithmetic(arithmeticNames);
                if (balanceArithmetic != null) {
                    list.add(balanceArithmetic);
                }
            }
        }
        return new CustomMode(list, consumerService, producerServices);
    }
}
