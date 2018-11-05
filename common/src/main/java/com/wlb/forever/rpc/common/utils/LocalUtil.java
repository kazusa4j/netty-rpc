package com.wlb.forever.rpc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Auther: william
 * @Date: 18/11/5 09:52
 * @Description:
 */
@Slf4j
public class LocalUtil {
    public static String getLocalIp() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("获取本机IP失败");
        }
        return ip;
    }

}
