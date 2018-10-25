package com.wlb.forever.rpc.common.protocol.request;


import com.wlb.forever.rpc.common.protocol.Packet;

import static com.wlb.forever.rpc.common.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * @Auther: william
 * @Date: 18/10/18 09:18
 * @Description:
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
