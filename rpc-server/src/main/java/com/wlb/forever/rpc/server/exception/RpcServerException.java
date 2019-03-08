package com.wlb.forever.rpc.server.exception;

/**
 * @Auther: william
 * @Date: 18/11/16 09:20
 * @Description:
 */
public class RpcServerException extends RuntimeException  {
    public RpcServerException() {
        super("RPC服务器出现异常！");
    }

    public RpcServerException(String message) {
        super(message);
    }

    public RpcServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
