package com.wlb.forever.rpc.server.executor.factory;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.balance.BalanceArithmeticFactory;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;
import com.wlb.forever.rpc.server.executor.mode.custom.CustomMode;
import org.springframework.beans.factory.annotation.Autowired;

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
        List<BalanceArithmetic> list = balanceArithmeticFactory.getArithmetics();
        return new CustomMode(list, consumerService, producerServices);
    }
}
