package com.wlb.forever.rpc.common.utils;

import java.util.UUID;

/**
 * @Auther: william
 * @Date: 18/10/22 14:32
 * @Description:
 */
public class UniqueIdUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
