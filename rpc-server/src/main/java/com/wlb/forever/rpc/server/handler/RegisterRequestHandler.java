package com.wlb.forever.rpc.server.handler;

import com.wlb.forever.rpc.common.protocol.request.RegisterServerRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.RegisterServerResponsePacket;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.utils.ServiceUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/18 11:18
 * @Description: 接收注册服务请求HANDLER
 */
@ChannelHandler.Sharable
@Slf4j
public class RegisterRequestHandler extends SimpleChannelInboundHandler<RegisterServerRequestPacket> {
    public static final RegisterRequestHandler INSTANCE = new RegisterRequestHandler();

    private RegisterRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RegisterServerRequestPacket registerServerRequestPacket) throws Exception {
        registerServerRequestPacket.setVersion(registerServerRequestPacket.getVersion());
        Service service = registerServerRequestPacket.getService();
        //注册服务
        ServiceUtil.bindService(service, channelHandlerContext.channel());
        RegisterServerResponsePacket registerServerResponsePacket = new RegisterServerResponsePacket();
        registerServerResponsePacket.setResult(true);
        //注册服务响应
        channelHandlerContext.writeAndFlush(registerServerResponsePacket);
    }

}
