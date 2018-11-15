package com.wlb.forever.rpc.common.protocol.response;

import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.PRODUCER_SERVICE_RESPONSE;


/**
 * @Auther: william
 * @Date: 18/10/19 16:05
 * @Description:服务器调用服务响应包
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerServiceResponsePacket extends AbstractPacket {
    private RpcResponseInfo rpcResponseInfo;

    @Override
    public Byte getCommand() {
        return PRODUCER_SERVICE_RESPONSE;
    }
}
