package com.wlb.forever.rpc.server.balance.arithmetic.round;

import com.wlb.forever.rpc.common.entity.Service;
import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.server.balance.arithmetic.random.RandomArithmetic;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/11/2 09:28
 * @Description:
 */
@Slf4j
public class RoundArithmetic implements BalanceArithmetic {
    private static final Map<String, List<Map<Service, Integer>>> roundMap = new ConcurrentHashMap<>();

    private RoundArithmetic() {

    }

    private static class RoundArithmeticHolder {
        private static final RoundArithmetic INSTANCE = new RoundArithmetic();
    }

    public static RoundArithmetic getInstance() {
        return RoundArithmeticHolder.INSTANCE;
    }

    @Override
    public List<Service> filterProducerServices(Service consumerService, List<Service> producerServices) {
        log.info("调用轮询算法");
        String serviceName = producerServices.get(0).getServiceName();
        List<Map<Service, Integer>> list;
        Map<Service, Integer> map = null;
        if (roundMap.containsKey(serviceName)) {
            list = roundMap.get(serviceName);
            boolean hasGetMap = false;
            for (Map<Service, Integer> mapTmp : list) {
                if (mapTmp.size() == producerServices.size()) {
                    hasGetMap = true;
                    for (Service service : producerServices) {
                        if (!mapTmp.containsKey(service)) {
                            hasGetMap = false;
                            break;
                        }
                    }
                    if (hasGetMap) {
                        map = mapTmp;
                        break;
                    }
                }
            }
            synchronized (roundMap) {
                if (!hasGetMap) {
                    map = new LinkedHashMap<>(16, 0.75f, true);
                    for (Service service : producerServices) {
                        map.put(service, 0);
                    }
                    list.add(map);
                }
            }
        } else {
            synchronized (roundMap) {
                list = new ArrayList<>();
                map = new LinkedHashMap<>(16, 0.75f, true);
                for (Service service : producerServices) {
                    map.put(service, 0);
                }
                list.add(map);
                roundMap.put(serviceName, list);
            }
        }
        return getService(map);
    }

    /**
     * 获取第一个生产者Service
     *
     * @param map
     * @return
     */
    private List<Service> getService(Map<Service, Integer> map) {
        List<Service> list = new ArrayList<>();
        if (!map.isEmpty()) {
            for (Service service : map.keySet()) {
                map.get(service);
                list.add(service);
                return list;
            }
        }
        return null;
    }
}
