package com.wlb.forever.rpc.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: william
 * @Date: 18/10/18 11:28
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private String serviceId;
    private String serviceType;
    private String serviceName;
}
