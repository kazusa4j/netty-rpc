package com.wlb.forever.rpc.client;

import com.wlb.forever.rpc.client.handler.ConsumerServiceResponseHandler;
import com.wlb.forever.rpc.client.handler.HeartBeatTimerHandler;
import com.wlb.forever.rpc.client.handler.ProducerServiceRequestHandler;
import com.wlb.forever.rpc.client.handler.RpcClientHandler;
import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.handler.PacketCodecHandler;
import com.wlb.forever.rpc.common.handler.RPCIdleStateHandler;
import com.wlb.forever.rpc.common.handler.UnPacketHandler;
import com.wlb.forever.rpc.common.protocol.request.RegisterServerRequestPacket;
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
import org.springframework.beans.factory.annotation.Value;
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
@Data
public class RpcClientStarter {

    public static volatile int STATUS = 1;//1:未连接；2:连接中;3:已连接
    private static NioEventLoopGroup workerGroup;
    private static Bootstrap bootstrap;
    public static Channel channel;

    /**
     * RPC服务器连接配置
     */
    private static String SERVER_HOST;
    private static int SERVER_PORT;

    @Value("${wlb.rpc.server.host:127.0.0.1}")
    private void setServerHost(String serverHost) {
        this.SERVER_HOST = serverHost;
    }

    @Value("${wlb.rpc.server.port:11211}")
    private void setPortHost(int serverPort) {
        this.SERVER_PORT = serverPort;
    }

    /**
     * 客户端配置
     */
    public static final String SERVICE_ID = UniqueIdUtil.getUUID();

    public static String SERVICE_NAME;
    private static int INIT_RETRY_TIME;
    private static boolean AWAYS_RETRY;
    private static int AWAYS_RETRY_INTERVAL;


    @Value("${wlb.rpc.client.servicename:client}")
    private void setServiceName(String serviceName) {
        this.SERVICE_NAME = serviceName;
    }

    @Value("${wlb.rpc.client.init-retry-time:5}")
    private void setInitRetryTime(int initRetryTime) {
        this.INIT_RETRY_TIME = initRetryTime;
    }

    @Value("${wlb.rpc.client.aways-retry:true}")
    private void setAwaysRetry(boolean awaysRetry) {
        this.AWAYS_RETRY = awaysRetry;
    }

    @Value("${wlb.rpc.client.aways-retry-interval:60}")
    private void setAwaysRetryInterval(int awaysRetryInterval) {
        this.AWAYS_RETRY_INTERVAL = awaysRetryInterval;
    }


    /**
     * 启动RPC客户端
     */
    @PostConstruct
    public synchronized static void start() {
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

        connect(bootstrap, SERVER_HOST, SERVER_PORT, INIT_RETRY_TIME);
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
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                STATUS = 3;
                log.info("RPC服务器连接成功");
                RpcClientStarter.channel = ((ChannelFuture) future).channel();
                channel.writeAndFlush(new RegisterServerRequestPacket(new Service(SERVICE_ID, SERVICE_NAME, "")));
            } else if (retry == 0) {
                log.info(AWAYS_RETRY_INTERVAL + "秒后尝试重连RPC服务器");
                if (!AWAYS_RETRY) {
                    return;
                }
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, 0), AWAYS_RETRY_INTERVAL, TimeUnit.SECONDS);
            } else {
                // 第几次重连
                int order = (INIT_RETRY_TIME - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.info(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
