package com.wlb.forever.rpc.common.constant;

/**
 * @Auther: william
 * @Date: 18/10/25 11:34
 * @Description:
 */
public interface RpcResponseCode {
    int SUCCESS=0;
    int NO_SERVICE = 1;
    int NO_BEAN = 2;
    int NO_METHOD = 3;
    int SERVER_EXCEPTION = 4;
    int SERVICE_EXCEPTION = 5;
}
