package com.wlb.forever.rpc.common.protocol.response;


import com.wlb.forever.rpc.common.protocol.AbstractPacket;

import static com.wlb.forever.rpc.common.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * @Auther: william
 * @Date: 18/10/18 09:21
 * @Description:心跳响应包
 */
public class HeartBeatResponsePacket extends AbstractPacket {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
