package com.wlb.forever.rpc.client.executor;

import com.wlb.forever.rpc.common.utils.SpringBeanUtil;

/**
 * @Auther: william
 * @Date: 18/10/25 09:21
 * @Description:
 */
public interface MessageSendExecutorLoader {
    MessageSendExecutor messageSendExecutor = SpringBeanUtil.getBean(MessageSendExecutor.class);
}
