package com.example.crypto.configuration;

import com.example.crypto.taskscheduler.BitcoinThreadPoolTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(
        basePackages = "com.example.crypto.taskscheduler",
        basePackageClasses = {BitcoinThreadPoolTaskScheduler.class})
public class ThreadPoolTaskSchedulerConfig {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(appProperties.getBitcoinPollingThreads());
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}