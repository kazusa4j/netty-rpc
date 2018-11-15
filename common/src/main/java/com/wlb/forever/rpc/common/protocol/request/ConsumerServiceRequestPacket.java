package com.wlb.forever.rpc.common.protocol.request;

import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.CONSUMER_SERVICE_REQUEST;


/**
 * @Auther: william
 * @Date: 18/10/19 09:46
 * @Description:客户端调用服务包
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerServiceRequestPacket extends AbstractPacket {
    private RpcRequestInfo rpcRequestInfo;

    @Override
    public Byte getCommand() {
        return CONSUMER_SERVICE_REQUEST;
    }
}
