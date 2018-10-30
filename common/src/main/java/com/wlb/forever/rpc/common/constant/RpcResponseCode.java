package com.wlb.forever.rpc.common.constant;

/**
 * @Auther: william
 * @Date: 18/10/25 11:34
 * @Description: RPC调用请求返回结果码
 */
public interface RpcResponseCode {
    //请求成功
    int SUCCESS = 0;
    //请求服务不存在
    int NO_SERVICE = 1;
    //请求调用对象BEAN不存在
    int NO_BEAN = 2;
    //请求方法不存在
    int NO_METHOD = 3;
    //服务调用出现异常
    int SERVER_EXCEPTION = 4;
    //RPC服务器出现异常
    int SERVICE_EXCEPTION = 5;
}
