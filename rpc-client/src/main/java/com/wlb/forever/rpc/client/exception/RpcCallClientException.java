package com.wlb.forever.rpc.client.exception;

/**
 * @Auther: william
 * @Date: 18/10/24 12:04
 * @Description: RPC请求异常
 */
public class RpcCallClientException extends RuntimeException {
    public RpcCallClientException() {
        super("RPC调用出现异常！");
    }

    public RpcCallClientException(String message) {
        super(message);
    }

    public RpcCallClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
