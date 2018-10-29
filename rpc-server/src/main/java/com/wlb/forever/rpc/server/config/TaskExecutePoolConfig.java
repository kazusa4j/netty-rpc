package com.wlb.forever.rpc.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: william
 * @Date: 18/10/26 09:10
 * @Description: 线程池配置
 */
@Configuration
@EnableAsync
public class TaskExecutePoolConfig {

    /**
     * 处理客户端请求线程池
     *
     * @return
     */
    @Bean(name = "threadPoolClientRequest")
    public ThreadPoolTaskExecutor ThreadPoolTaskExecutorClientRequest() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        setExecutor(executor, 4, 8, 100, 60, "Pool-RPC-Client-Request");
        executor.initialize();
        return executor;
    }

    /**
     * 处理服务器请求返回线程池
     *
     * @return
     */
    @Bean(name = "threadPoolServerResponse")
    public ThreadPoolTaskExecutor ThreadPoolTaskExecutorServerResponse() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        setExecutor(executor, 4, 8, 100, 60, "Pool-RPC-Server-Response");
        executor.initialize();
        return executor;
    }

    /**
     * 配置执行器
     *
     * @param executor
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueCapacity
     * @param keepAliveSeconds
     * @param threadNamePrefix
     */
    private void setExecutor(ThreadPoolTaskExecutor executor, int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds, String threadNamePrefix) {
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
