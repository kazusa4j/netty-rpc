package com.wlb.forever.rpc.common.protocol.request;

import com.wlb.forever.rpc.common.entity.RpcRequestInfo;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.PRODUCER_SERVICE_REQUEST;


/**
 * @Auther: william
 * @Date: 18/10/19 15:58
 * @Description:服务器调用服务请求包
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerServiceRequestPacket extends AbstractPacket {
    private RpcRequestInfo rpcRequestInfo;

    @Override
    public Byte getCommand() {
        return PRODUCER_SERVICE_REQUEST;
    }
}
