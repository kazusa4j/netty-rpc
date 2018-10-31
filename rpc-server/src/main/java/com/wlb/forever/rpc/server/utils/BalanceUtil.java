package com.wlb.forever.rpc.server.utils;

import com.wlb.forever.rpc.server.balance.BalanceMode;
import com.wlb.forever.rpc.server.balance.fastest.FastestMode;
import com.wlb.forever.rpc.server.balance.round.RoundRobinMode;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/31 16:44
 * @Description:
 */
public class BalanceUtil {

    public static BalanceMode getBalanceMode(String modeType, Channel channel, List<String> list) {
        if (modeType == null) {
            return null;
        }
        switch (modeType) {
            case "FASTEST":
                return new FastestMode(channel, list);
            case "ROUND":
                return new RoundRobinMode(channel, list);
            default:
                return null;
        }
    }
}
