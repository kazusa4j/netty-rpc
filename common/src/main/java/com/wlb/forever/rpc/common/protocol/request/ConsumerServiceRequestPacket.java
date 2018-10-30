package com.wlb.forever.rpc.common.protocol.request;

import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.CONSUMER_SERVICE_REQUEST;


/**
 * @Auther: william
 * @Date: 18/10/19 09:46
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerServiceRequestPacket extends Packet {
    private RpcRequestInfo rpcRequestInfo;

    @Override
    public Byte getCommand() {
        return CONSUMER_SERVICE_REQUEST;
    }
}
