package com.wlb.forever.rpc.common.protocol.response;

import com.wlb.forever.rpc.common.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.CLIENT_SERVICE_RESPONSE;

/**
 * @Auther: william
 * @Date: 18/10/19 10:02
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientServiceResponsePacket extends Packet {
    private String requestId;
    private Integer code = 0;
    private String desc = "";
    private Object result=null;

    @Override
    public Byte getCommand() {
        return CLIENT_SERVICE_RESPONSE;
    }
}
