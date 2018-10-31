package com.wlb.forever.rpc.client.exception;

/**
 * @Auther: william
 * @Date: 18/10/31 10:33
 * @Description: RPC生产者自定义异常
 */
public class RpcProducerException extends RuntimeException {
    public RpcProducerException() {
        super("RPC调用出现异常！");
    }

    public RpcProducerException(String message) {
        super(message);
    }

    public RpcProducerException(String message, Throwable cause) {
        super(message, cause);
    }
}
