package com.wlb.forever.rpc.common.protocol;

import com.wlb.forever.rpc.common.protocol.request.ClientServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.HeartBeatRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.RegisterServerRequestPacket;
import com.wlb.forever.rpc.common.protocol.request.ServerServiceRequestPacket;
import com.wlb.forever.rpc.common.protocol.response.ClientServiceResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.HeartBeatResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.RegisterServerResponsePacket;
import com.wlb.forever.rpc.common.protocol.response.ServerServiceResponsePacket;
import com.wlb.forever.rpc.common.serializer.Serializer;
import com.wlb.forever.rpc.common.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.wlb.forever.rpc.common.protocol.command.Command.*;


/**
 * @Auther: william
 * @Date: 18/10/17 16:32
 * @Description:
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x11910898;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(REGISTER_SERVER_REQUEST, RegisterServerRequestPacket.class);
        packetTypeMap.put(REGISTER_SERVER_RESPONSE, RegisterServerResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
        packetTypeMap.put(CLIENT_SERVICE_REQUEST, ClientServiceRequestPacket.class);
        packetTypeMap.put(CLIENT_SERVICE_RESPONSE, ClientServiceResponsePacket.class);
        packetTypeMap.put(SERVER_SERVICE_REQUEST, ServerServiceRequestPacket.class);
        packetTypeMap.put(SERVER_SERVICE_RESPONSE, ServerServiceResponsePacket.class);
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
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

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
