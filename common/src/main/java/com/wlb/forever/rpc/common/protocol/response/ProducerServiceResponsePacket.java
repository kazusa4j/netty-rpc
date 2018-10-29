package com.wlb.forever.rpc.common.protocol.response;

import com.wlb.forever.rpc.common.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.SERVER_SERVICE_RESPONSE;


/**
 * @Auther: william
 * @Date: 18/10/19 16:05
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerServiceResponsePacket extends Packet {
    private String requestId;
    private String fromServiceId;
    private String fromServiceName;
    private Integer code = 0;
    private String desc = "";
    private Object result=null;

    @Override
    public Byte getCommand() {
        return SERVER_SERVICE_RESPONSE;
    }
}
