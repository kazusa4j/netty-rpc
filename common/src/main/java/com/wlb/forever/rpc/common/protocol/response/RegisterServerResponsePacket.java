package com.wlb.forever.rpc.common.protocol.response;

import com.wlb.forever.rpc.common.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wlb.forever.rpc.common.protocol.command.Command.REGISTER_SERVER_RESPONSE;


/**
 * @Auther: william
 * @Date: 18/10/18 10:28
 * @Description:注册服务响应包
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterServerResponsePacket extends Packet {
    private Boolean result;

    @Override
    public Byte getCommand() {
        return REGISTER_SERVER_RESPONSE;
    }
}
