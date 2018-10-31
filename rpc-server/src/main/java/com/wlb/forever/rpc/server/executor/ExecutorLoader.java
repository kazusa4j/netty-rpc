package com.wlb.forever.rpc.server.executor;

import com.wlb.forever.rpc.common.utils.SpringBeanUtil;

/**
 * @Auther: william
 * @Date: 18/10/26 09:51
 * @Description:
 */
public interface ExecutorLoader {
    ConsumerRequestExecutor CLIENT_REQUEST_EXECUTOR = (ConsumerRequestExecutor) SpringBeanUtil.getBean(ConsumerRequestExecutor.class);
    ProducerResponseExecutor SERVER_RESPONSE_EXECUTOR = (ProducerResponseExecutor) SpringBeanUtil.getBean(ProducerResponseExecutor.class);
}
