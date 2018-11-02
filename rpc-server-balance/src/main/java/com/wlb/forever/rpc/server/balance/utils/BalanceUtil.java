package com.wlb.forever.rpc.server.balance.utils;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.mode.FastestMode;
import com.wlb.forever.rpc.server.balance.mode.FastestSuccessMode;
import com.wlb.forever.rpc.server.balance.mode.RandomMode;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 16:44
 * @Description:
 */
public class BalanceUtil {

    public static BalanceMode getBalanceMode(String modeType, Service consumerService, List<Service> producerServices) {
        if (modeType == null) {
            return null;
        }
        switch (modeType) {
            case "FASTEST":
                return new FastestMode(consumerService, producerServices);
            case "FASTESTSUCCESS":
                return new FastestSuccessMode(consumerService, producerServices);
            case "RANDOM":
                return new RandomMode(consumerService, producerServices);
            default:
                return null;
        }
    }
}
