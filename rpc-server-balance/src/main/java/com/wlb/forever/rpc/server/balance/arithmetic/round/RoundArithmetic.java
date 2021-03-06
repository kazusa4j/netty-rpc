package com.wlb.forever.rpc.server.balance.arithmetic.round;

import com.wlb.forever.rpc.common.balance.BalanceArithmetic;
import com.wlb.forever.rpc.common.entity.Service;
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
    private static final ConcurrentHashMap<String, List<Map<Service, Integer>>> ROUND_MAP = new ConcurrentHashMap<>();

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
        if (ROUND_MAP.containsKey(serviceName)) {
            list = ROUND_MAP.get(serviceName);
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

            if (!hasGetMap) {
                map = new LinkedHashMap<>(16, 0.75f, true);
                for (Service service : producerServices) {
                    map.put(service, 0);
                }
                synchronized (ROUND_MAP) {
                    list.add(map);
                }
            }
        } else {
            list = new ArrayList<>();
            map = new LinkedHashMap<>(16, 0.75f, true);
            for (Service service : producerServices) {
                map.put(service, 0);
            }
            list.add(map);
            synchronized (ROUND_MAP) {
                ROUND_MAP.put(serviceName, list);
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
                log.info(service.getServiceId());
                return list;
            }
        }
        return null;
    }
}
