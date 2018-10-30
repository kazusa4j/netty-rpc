package com.wlb.forever.rpc.common.utils;

import java.util.UUID;

/**
 * @Auther: william
 * @Date: 18/10/22 14:32
 * @Description:唯一值工具类
 */
public class UniqueIdUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
