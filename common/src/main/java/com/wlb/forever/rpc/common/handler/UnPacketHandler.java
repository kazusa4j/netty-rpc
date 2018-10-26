package com.wlb.forever.rpc.common.handler;

import com.wlb.forever.rpc.common.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: william
 * @Date: 18/10/20 15:53
 * @Description:
 */
@Slf4j
public class UnPacketHandler extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public UnPacketHandler() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(0) != PacketCodec.MAGIC_NUMBER) {

            log.info("请求包与协议不匹配!");
            //ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
