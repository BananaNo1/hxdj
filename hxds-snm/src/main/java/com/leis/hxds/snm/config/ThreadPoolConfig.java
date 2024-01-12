package com.leis.hxds.snm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class ThreadPoolConfig {

    @Bean("AsyncTaskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(32);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("task-");
        /**
         * 当线程池和队列已满，无法接受新的任务时， ThreadPoolExecutor会调用提交新任务的线程来直接执行这个任务，而不是抛出异常或者丢弃任务。
         * 这意味着如果任务提交速度超过了线程池处理的速度，那么提交任务的线程将会暂时停止提交新任务并帮助处理积压的任务，直到有线程空闲出来。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
