package com.wlb.forever.rpc.server.balance.arithmetic.iphash;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: william
 * @Date: 18/11/5 09:42
 * @Description:
 */
@Slf4j
public class IpHashArithmetic implements BalanceArithmetic {
    private IpHashArithmetic() {

    }

    private static class IpHashArithmeticHolder {
        private static final IpHashArithmetic INSTANCE = new IpHashArithmetic();
    }

    public static IpHashArithmetic getInstance() {
        return IpHashArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        log.info("调用IP_HASH算法");
        if (consumerService == null || StringUtil.isBlank(consumerService.getIp())) {
            log.warn("ipHash算法客户端service或IP为空");
        }
        List<Service> list = new ArrayList<>();
        int ipHash = consumerService.getIp().hashCode();
        list.add(producerServices.get(ipHash % producerServices.size()));
        return list;
    }
}
