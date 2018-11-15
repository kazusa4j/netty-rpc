package com.wlb.forever.rpc.common.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: william
 * @Date: 18/10/17 16:29
 * @Description:RPC包抽象类
 */
@Data
public abstract class AbstractPacket implements Serializable {
    private static final long serialVersionUID = -3759642830732260075L;
    /**
     * 协议版本
     */
    private Byte version = 1;


    /**
     *
     * @return
     */
    public abstract Byte getCommand();
}
