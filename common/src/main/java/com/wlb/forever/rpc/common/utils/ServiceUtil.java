package com.wlb.forever.rpc.common.utils;

import com.wlb.forever.rpc.common.attritube.Attributes;
import com.wlb.forever.rpc.common.entity.Service;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/18 11:32
 * @Description: 注册服务工具类
 */
@Slf4j
public class ServiceUtil {
    private static final Map<String, Map<Service, Channel>> serviceMap = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param service
     * @param channel
     */
    public static void bindService(Service service, Channel channel) {
        synchronized (serviceMap) {
            if (serviceMap.containsKey(service.getServiceName())) {
                serviceMap.get(service.getServiceName()).put(service, channel);
            } else {
                Map<Service, Channel> map = new LinkedHashMap<>();
                map.put(service, channel);
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
            serviceMap.get(service.getServiceName()).remove(service);
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
     * @param service
     * @param serviceName
     * @return
     */
    public static Channel getChannel(Service service, String serviceName) {

        if (serviceMap.containsKey(serviceName)) {
            return serviceMap.get(serviceName).get(service);
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

    /**
     * 根据服务名获取服务Services
     *
     * @param serviceName
     * @return
     */
    public static List<Service> getChannelsServiceId(String serviceName) {
        if (serviceMap.containsKey(serviceName)) {
            return new ArrayList<>(serviceMap.get(serviceName).keySet());
        } else {
            return null;
        }
    }

    public static List<String> getServiceIdsByServices(List<Service> services) {
        List<String> serviceIds = new ArrayList<>();
        services.forEach(service -> {
            serviceIds.add(service.getServiceId());
        });
        return serviceIds;
    }
}
