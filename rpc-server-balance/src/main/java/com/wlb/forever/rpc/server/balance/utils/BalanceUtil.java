package com.wlb.forever.rpc.server.balance.utils;

import com.wlb.forever.rpc.common.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.mode.FastestMode;
import com.wlb.forever.rpc.server.balance.mode.FastestSuccessMode;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 16:44
 * @Description:
 */
public class BalanceUtil {

    public static BalanceMode getBalanceMode(String modeType, String consumerServiceId, String consumerServiceName, List<String> producerServiceIds) {
        if (modeType == null) {
            return null;
        }
        switch (modeType) {
            case "FASTEST":
                return new FastestMode(consumerServiceId, consumerServiceName, producerServiceIds);
            case "FASTESTSUCCESS":
                return new FastestSuccessMode(consumerServiceId, consumerServiceName, producerServiceIds);
            default:
                return null;
        }
    }
}
