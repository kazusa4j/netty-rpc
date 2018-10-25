package com.wlb.forever.rpc.common.protocol.response;


import com.wlb.forever.rpc.common.protocol.Packet;

import static com.wlb.forever.rpc.common.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * @Auther: william
 * @Date: 18/10/18 09:21
 * @Description:
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
