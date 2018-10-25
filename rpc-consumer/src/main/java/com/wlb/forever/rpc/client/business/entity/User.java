package com.wlb.forever.rpc.client.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: william
 * @Date: 18/10/18 09:44
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 1481161726302269330L;
    private Integer id;
    private String name;
    private Date createTime;
}
