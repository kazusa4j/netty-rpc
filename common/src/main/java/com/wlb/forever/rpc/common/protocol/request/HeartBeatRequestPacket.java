package com.wlb.forever.rpc.common.protocol.request;


import com.wlb.forever.rpc.common.protocol.AbstractPacket;

import static com.wlb.forever.rpc.common.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * @Auther: william
 * @Date: 18/10/18 09:18
 * @Description:心跳请求包
 */
public class HeartBeatRequestPacket extends AbstractPacket {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
