package com.wlb.forever.rpc.client;

import com.wlb.forever.rpc.client.config.RpcClientConfig;
import com.wlb.forever.rpc.client.config.RpcServerConfig;
import com.wlb.forever.rpc.client.handler.HeartBeatTimerHandler;
import com.wlb.forever.rpc.client.handler.RpcClientHandler;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.handler.PacketCodecHandler;
import com.wlb.forever.rpc.common.handler.RPCIdleStateHandler;
import com.wlb.forever.rpc.common.handler.UnPacketHandler;
import com.wlb.forever.rpc.common.protocol.request.RegisterServerRequestPacket;
import com.wlb.forever.rpc.common.utils.LocalUtil;
import com.wlb.forever.rpc.common.utils.UniqueIdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @Auther: william
 * @Date: 18/10/17 17:18
 * @Description: RPC 客户端启动器
 */
@Component
@Slf4j
public class RpcClientStarter {

    public static volatile int STATUS = 1;//1:未连接；2:连接中;3:已连接
    private static NioEventLoopGroup workerGroup;
    private static Bootstrap bootstrap;
    public static Channel channel;
    @Autowired
    private RpcServerConfig rpcServerConfig;
    @Autowired
    private RpcClientConfig rpcClientConfig;

    private static final String SERVICE_ID = UniqueIdUtil.getUUID();
    private static final String SERVICE_IP = LocalUtil.getLocalIp();
    public static final Service SERVICE = new Service(SERVICE_ID, "", "", SERVICE_IP);


    /**
     * 启动RPC客户端
     */
    @PostConstruct
    public synchronized void start() {
        if (STATUS != 1) {
            log.info("RPC客户端不处于未启动状态");
            return;
        }
        STATUS = 2;
        bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        // 空闲检测
                        ch.pipeline().addLast(new RPCIdleStateHandler());
                        // 拆包
                        ch.pipeline().addLast(new UnPacketHandler());
                        // 编解码
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                        // RPC客户端handler
                        ch.pipeline().addLast(RpcClientHandler.INSTANCE);
                    }
                });

        connect(bootstrap, rpcServerConfig.getHost(), rpcServerConfig.getPort(), rpcClientConfig.getInitRetryTime());
    }


    public static void destroy() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully().syncUninterruptibly();
            log.info("关闭 Netty Client");
        }
    }

    /**
     * 连接RPC服务器
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                STATUS = 3;
                log.info("RPC服务器连接成功");
                RpcClientStarter.channel = ((ChannelFuture) future).channel();
                SERVICE.setServiceName(RpcClientConfig.getServiceName());
                log.error(SERVICE.toString());
                channel.writeAndFlush(new RegisterServerRequestPacket(SERVICE));
            } else if (retry == 0) {
                log.info(rpcClientConfig.getAwaysRetryInterval() + "秒后尝试重连RPC服务器");
                if (!rpcClientConfig.getAwaysRetry()) {
                    return;
                }
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, 0), rpcClientConfig.getAwaysRetryInterval(), TimeUnit.SECONDS);
            } else {
                // 第几次重连
                int order = (rpcClientConfig.getInitRetryTime() - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.info(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
