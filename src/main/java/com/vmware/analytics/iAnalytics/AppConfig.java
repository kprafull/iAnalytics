package com.vmware.analytics.iAnalytics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ComponentScan(
        basePackages = {
                "com.vmware.analytics.controller"
        })
public class AppConfig {
    @Value("${migration.taskExecutor.threadpoolSize:20}")
    int taskExecutorThreadPoolSize;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(taskExecutorThreadPoolSize);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("exec-");
        executor.initialize();
        return executor;
    }
}
