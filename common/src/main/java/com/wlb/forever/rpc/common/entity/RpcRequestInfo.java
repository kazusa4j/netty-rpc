package com.wlb.forever.rpc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: william
 * @Date: 18/10/29 17:48
 * @Description:RPC调用请求信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequestInfo implements Serializable {
    private static final long serialVersionUID = -6828549150415093442L;
    private String requestId;
    private String toServiceName;
    private String fromServiceId;
    private String fromServiceName;
    private String beanName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
}
