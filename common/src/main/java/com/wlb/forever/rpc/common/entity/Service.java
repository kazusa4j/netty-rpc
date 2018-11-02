package com.wlb.forever.rpc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: william
 * @Date: 18/10/18 11:28
 * @Description: 服务对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service implements Serializable {
    private static final long serialVersionUID = -4143787395794755802L;
    private String serviceId;
    private String serviceName;
    private String serviceType;
}
