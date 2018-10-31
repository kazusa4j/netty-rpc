package com.wlb.forever.rpc.client.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    @ApiModelProperty(required = true, value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(required = true, value = "name", example = "william")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true, value = "createTime", example = "2018-01-01 00:00:00")
    private Date createTime;
}
