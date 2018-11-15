package com.wlb.forever.rpc.common.handler;

import com.wlb.forever.rpc.common.protocol.AbstractPacket;
import com.wlb.forever.rpc.common.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @Auther: william
 * @Date: 18/10/17 16:47
 * @Description:编解码包HANDLER
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, AbstractPacket> {
    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    /**
     * 解码
     * @param ctx
     * @param byteBuf
     * @param out
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        out.add(PacketCodec.INSTANCE.decode(byteBuf));
    }

    /**
     * 编码
     * @param ctx
     * @param abstractPacket
     * @param out
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket abstractPacket, List<Object> out) {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, abstractPacket);
        out.add(byteBuf);
    }
}
