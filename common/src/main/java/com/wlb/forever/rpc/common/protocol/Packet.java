package com.wlb.forever.rpc.common.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: william
 * @Date: 18/10/17 16:29
 * @Description:
 */
@Data
public abstract class Packet implements Serializable {
    private static final long serialVersionUID = -3759642830732260075L;
    /**
     * 协议版本
     */
    //@JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    //@JSONField(serialize = false)
    public abstract Byte getCommand();
}
