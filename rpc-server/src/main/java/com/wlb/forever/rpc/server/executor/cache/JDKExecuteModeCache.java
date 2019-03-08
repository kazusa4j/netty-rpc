package com.wlb.forever.rpc.server.executor.cache;

import com.wlb.forever.rpc.server.exception.RpcServerException;
import com.wlb.forever.rpc.server.executor.mode.ServerRpcExecuteMode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/31 16:06
 * @Description: RPC调用使用JDK做模式缓存
 */
public class JDKExecuteModeCache implements ExecuteModeCache {
    private final static Map<String, ServerRpcExecuteMode> BALANCE_MODE_MAP = new ConcurrentHashMap<>();

    @Override
    public void put(String requestId, ServerRpcExecuteMode serverRpcExecuteMode) {
        if (BALANCE_MODE_MAP.containsKey(requestId)) {
            throw new RpcServerException("RPC调用使用JDK做模式缓存，插入模式ID已存在");
        }
        BALANCE_MODE_MAP.put(requestId, serverRpcExecuteMode);
    }

    @Override
    public ServerRpcExecuteMode getBalanceMode(String requestId) {
        if (!BALANCE_MODE_MAP.containsKey(requestId)) {
            return null;
        }
        return BALANCE_MODE_MAP.get(requestId);
    }

    @Override
    public void removeBalanceMode(String requestId) {
        BALANCE_MODE_MAP.remove(requestId);
    }
}
