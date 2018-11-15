package com.wlb.forever.rpc.common.protocol.response;

import com.wlb.forever.rpc.common.entity.RpcResponseInfo;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.CONSUMER_SERVICE_RESPONSE;

/**
 * @Auther: william
 * @Date: 18/10/19 10:02
 * @Description:客户端调用服务响应包
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsumerServiceResponsePacket extends AbstractPacket {
    private RpcResponseInfo rpcResponseInfo;

    @Override
    public Byte getCommand() {
        return CONSUMER_SERVICE_RESPONSE;
    }
}
