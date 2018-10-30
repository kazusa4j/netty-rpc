package com.wlb.forever.rpc.common.protocol.command;

/**
 * @Auther: william
 * @Date: 18/10/17 16:33
 * @Description: 包指令码
 */
public interface Command {
    //注册服务请求包
    Byte REGISTER_SERVER_REQUEST = 1;
    //注册服务响应包
    Byte REGISTER_SERVER_RESPONSE = 2;
    //心跳请求包
    Byte HEARTBEAT_REQUEST = 3;
    //心跳响应包
    Byte HEARTBEAT_RESPONSE = 4;
    //客户端调用服务包
    Byte CONSUMER_SERVICE_REQUEST = 5;
    //客户端调用服务响应包
    Byte CONSUMER_SERVICE_RESPONSE = 6;
    //服务器调用服务请求包
    Byte PRODUCER_SERVICE_REQUEST = 7;
    //服务器调用服务响应包
    Byte PRODUCER_SERVICE_RESPONSE = 8;

}
