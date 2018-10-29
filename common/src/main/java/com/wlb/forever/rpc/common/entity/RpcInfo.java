package com.wlb.forever.rpc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: william
 * @Date: 18/10/29 17:48
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcInfo {
    private String requestId;
    private String toServiceName;
    private String fromServiceId;
    private String fromServiceName;
    private String beanName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
}
