package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.utils.SpringContextUtil;

/**
 * @Auther: william
 * @Date: 18/10/26 09:51
 * @Description:
 */
public interface ExecutorLoader {
    ClientRequestExecutor CLIENT_REQUEST_EXECUTOR = SpringContextUtil.getBean(ClientRequestExecutor.class);
    ServerResponseExecutor SERVER_RESPONSE_EXECUTOR = SpringContextUtil.getBean(ServerResponseExecutor.class);
}
