package com.wlb.forever.rpc.client.exception;

/**
 * @Auther: william
 * @Date: 18/10/24 12:04
 * @Description:
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
