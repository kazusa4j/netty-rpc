package com.wlb.forever.rpc.client.exception;

/**
 * @Auther: william
 * @Date: 18/10/24 12:04
 * @Description: 自定义RPC消费异常
 */
public class RpcConsumerException extends RuntimeException {
    public RpcConsumerException() {
        super("RPC调用出现异常！");
    }

    public RpcConsumerException(String message) {
        super(message);
    }

    public RpcConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
