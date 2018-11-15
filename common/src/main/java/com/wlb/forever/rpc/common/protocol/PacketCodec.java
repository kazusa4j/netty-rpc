package com.wlb.forever.rpc.common.protocol;

import com.wlb.forever.rpc.common.config.RpcCommonConfig;
import com.wlb.forever.rpc.common.protocol.request.ConsumerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.HeartBeatRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.RegisterServerRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ProducerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ConsumerServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.HeartBeatResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.RegisterServerResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ProducerServiceResponsePacket;
import com.wlb.forever.rpc.common.serializer.Serializer;
import com.wlb.forever.rpc.common.serializer.impl.HessianSerilizer;
import com.wlb.forever.rpc.common.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.wlb.forever.rpc.common.protocol.command.Command.*;


/**
 * @Auther: william
 * @Date: 18/10/17 16:32
 * @Description:包编解码
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x11910898;
    public static final PacketCodec INSTANCE = new PacketCodec();
    private Serializer defaultSerializer = getDefaultSerializer();

    private final Map<Byte, Class<? extends AbstractPacket>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(REGISTER_SERVER_REQUEST, RegisterServerRequestPacket.class);
        packetTypeMap.put(REGISTER_SERVER_RESPONSE, RegisterServerResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
        packetTypeMap.put(CONSUMER_SERVICE_REQUEST, ConsumerServiceRequestPacket.class);
        packetTypeMap.put(CONSUMER_SERVICE_RESPONSE, ConsumerServiceResponsePacket.class);
        packetTypeMap.put(PRODUCER_SERVICE_REQUEST, ProducerServiceRequestPacket.class);
        packetTypeMap.put(PRODUCER_SERVICE_RESPONSE, ProducerServiceResponsePacket.class);
        serializerMap = new HashMap<>();
        Serializer jSONSerializer = new JSONSerializer();
        Serializer hessianSerilizer = new HessianSerilizer();
        serializerMap.put(jSONSerializer.getSerializerAlgorithm(), jSONSerializer);
        serializerMap.put(hessianSerilizer.getSerializerAlgorithm(), hessianSerilizer);
    }

    public void encode(ByteBuf byteBuf, AbstractPacket abstractPacket) {
        // 1. 序列化 java 对象
        byte[] bytes = defaultSerializer.serialize(abstractPacket);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(abstractPacket.getVersion());
        byteBuf.writeByte(defaultSerializer.getSerializerAlgorithm());
        byteBuf.writeByte(abstractPacket.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public AbstractPacket decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends AbstractPacket> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends AbstractPacket> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }


    private static Serializer getDefaultSerializer() {
        switch (RpcCommonConfig.ENCODE) {
            case "JSON":
                return new JSONSerializer();
            case "HESSIAN":
                return new HessianSerilizer();
            default:
                return new JSONSerializer();
        }
    }
}
