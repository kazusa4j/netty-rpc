package com.wlb.forever.rpc.common.protocol.request;

import com.wlb.forever.rpc.common.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.PRODUCER_SERVICE_REQUEST;


/**
 * @Auther: william
 * @Date: 18/10/19 15:58
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerServiceRequestPacket extends Packet {
    private String requestId;
    private String fromServiceId;
    private String fromServiceName;
    private String beanName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
    @Override
    public Byte getCommand() {
        return PRODUCER_SERVICE_REQUEST;
    }
}
