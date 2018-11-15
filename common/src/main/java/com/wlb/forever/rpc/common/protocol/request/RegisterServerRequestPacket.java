package com.wlb.forever.rpc.common.protocol.request;


import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import lombok.*;

import static com.wlb.forever.rpc.common.protocol.command.Command.REGISTER_SERVER_REQUEST;


/**
 * @Auther: william
 * @Date: 18/10/17 17:00
 * @Description:注册服务请求包
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterServerRequestPacket extends AbstractPacket {
    private Service service;

    @Override
    public Byte getCommand() {
        return REGISTER_SERVER_REQUEST;
    }
}
