package com.wlb.forever.rpc.server.utils;

import com.wlb.forever.rpc.server.attritube.Attributes;
import com.wlb.forever.rpc.server.entity.Service;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/18 11:32
 * @Description:
 */
@Slf4j
public class ServiceUtil {
    private static final Map<String, Map<String, Channel>> serviceMap = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param service
     * @param channel
     */
    public static void bindService(Service service, Channel channel) {
        synchronized (serviceMap) {
            if (serviceMap.containsKey(service.getServiceName())) {
                serviceMap.get(service.getServiceName()).put(service.getServiceId(), channel);
            } else {
                Map<String, Channel> map = new LinkedHashMap<>();
                map.put(service.getServiceId(), channel);
                serviceMap.put(service.getServiceName(), map);
            }
        }
        log.info("开始注册服务:" + service.getServiceName());
        channel.attr(Attributes.SERVICE).set(service);
        log.info("注册服务:" + service + "成功");
    }

    /**
     * 注销服务
     *
     * @param channel
     */
    public static void unBindService(Channel channel) {
        Service service = getService(channel);
        if (service != null) {
            serviceMap.get(service.getServiceName()).remove(service.getServiceId());
            channel.attr(Attributes.SERVICE).set(null);
            log.info(service.getServiceName() + " 服务注销!");
        }
    }

    /**
     * 获取Service
     *
     * @param channel
     * @return
     */
    public static Service getService(Channel channel) {
        if (channel.hasAttr(Attributes.SERVICE)) {
            return channel.attr(Attributes.SERVICE).get();
        } else {
            return null;
        }
    }

    /**
     * 根据服务名服务ID获取channel
     *
     * @param serviceId
     * @param serviceName
     * @return
     */
    public static Channel getChannel(String serviceId, String serviceName) {

        if (serviceMap.containsKey(serviceName)) {
            return serviceMap.get(serviceName).get(serviceId);
        } else {
            return null;
        }
    }

    /**
     * 根据服务名获取服务列表
     *
     * @param serviceName
     * @return
     */
    public static List<Channel> getChannels(String serviceName) {

        if (serviceMap.containsKey(serviceName)) {
            return new ArrayList<>(serviceMap.get(serviceName).values());
        } else {
            return null;
        }
    }
}