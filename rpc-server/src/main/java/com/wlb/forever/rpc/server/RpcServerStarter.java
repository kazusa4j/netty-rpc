package com.wlb.forever.rpc.server;

import com.wlb.forever.rpc.common.handler.PacketCodecHandler;
import com.wlb.forever.rpc.common.handler.RPCIdleStateHandler;
import com.wlb.forever.rpc.common.handler.UnPacketHandler;
import com.wlb.forever.rpc.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Auther: william
 * @Date: 18/10/17 15:49
 * @Description: RPC服务器启动器
 */
@Component
@Slf4j
public class RpcServerStarter {
    private NioEventLoopGroup boosGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();


    @Value("${wlb.rpc.server.port:11211}")
    private int nettyPort;

    @PostConstruct
    public void start() {

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //心跳
                        ch.pipeline().addLast(new RPCIdleStateHandler());
                        //拆包粘包验证协议
                        ch.pipeline().addLast(new UnPacketHandler());
                        //编码解码
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        //接收心跳
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        //RPC服务handler
                        ch.pipeline().addLast(RPCServerHandler.INSTANCE);

                    }
                });
        bind(serverBootstrap, nettyPort);
    }

    @PreDestroy
    public void destroy() {
        boosGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
        log.info("关闭 Netty RPC Server成功");
    }

    private void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("启动 Netty RPC Server成功" + ": 端口[" + port + "]!");
            } else {
                log.info("启动 Netty RPC Server失败" + ": 端口[" + port + "]!");
            }
        });
    }

}
