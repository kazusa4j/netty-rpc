package com.wlb.forever.rpc.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/10/30 14:24
 * @Description:
 */
@Component
public class RpcCommonUtil {
    public static String ENCODE;

    @Value("${wlb.rpc.common.encode:JSON}")
    private void setEncode(String encode) {
        this.ENCODE = encode;
    }
}
