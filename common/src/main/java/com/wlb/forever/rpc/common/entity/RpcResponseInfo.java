package com.wlb.forever.rpc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: william
 * @Date: 18/10/30 15:00
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponseInfo implements Serializable {
    private static final long serialVersionUID = -3730811428883162419L;
    private String requestId;
    private String fromServiceId;
    private String fromServiceName;
    private Integer code = 0;
    private String desc = "";
    private byte[] result;
}
