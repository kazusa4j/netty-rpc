package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.utils.SpringBeanUtil;

/**
 * @Auther: william
 * @Date: 18/10/26 09:51
 * @Description:
 */
public interface ExecutorLoader {
    ClientRequestExecutor CLIENT_REQUEST_EXECUTOR = (ClientRequestExecutor) SpringBeanUtil.getBean(ClientRequestExecutor.class);
    ServerResponseExecutor SERVER_RESPONSE_EXECUTOR = (ServerResponseExecutor) SpringBeanUtil.getBean(ServerResponseExecutor.class);
}
