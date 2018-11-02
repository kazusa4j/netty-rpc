package com.wlb.forever.rpc.common.entity;

import com.wlb.forever.rpc.common.config.RpcCommonConfig;
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
    private String encode = RpcCommonConfig.ENCODE;
    private String requestId;
    private String producerServiceName;
    private Service consumerService;
    private String beanName;
    private String methodName;
    private String[] paramTypes;
    private byte[][] params;
}
